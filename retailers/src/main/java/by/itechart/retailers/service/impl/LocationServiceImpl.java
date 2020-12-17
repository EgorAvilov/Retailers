package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.LocationConverter;
import by.itechart.retailers.dto.LocationDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.DeletedStatus;
import by.itechart.retailers.entity.Location;
import by.itechart.retailers.entity.LocationType;
import by.itechart.retailers.entity.Status;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.LocationRepository;
import by.itechart.retailers.repository.UserRepository;
import by.itechart.retailers.service.interfaces.LocationService;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final UserService userService;
    private final LocationRepository locationRepository;
    private final LocationConverter locationConverter;
    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);

    @Autowired
    public LocationServiceImpl(UserService userService,
                               LocationRepository locationRepository,
                               LocationConverter locationConverter,
                               UserRepository userRepository) {
        this.userService = userService;
        this.locationRepository = locationRepository;
        this.locationConverter = locationConverter;
        this.userRepository = userRepository;
    }


    @Override
    public LocationDto findById(long locationId) {
        logger.info("Find location by id {}", locationId);
        UserDto userDto = userService.getCurrentUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        Location location = locationRepository.findByIdAndCustomer_IdAndStatus(locationId, customerId, DeletedStatus.ACTIVE)
                                              .orElse(new Location());
        return locationConverter.entityToDto(location);
    }

    @Override
    public Page<LocationDto> findAll(Pageable pageable) {
        logger.info("Find all locations");
        UserDto userDto = userService.getCurrentUser();
        Page<Location> locationPage = locationRepository.findAllByCustomer_IdAndStatus(pageable, userDto.getCustomer()
                                                                                                        .getId(), DeletedStatus.ACTIVE);
        List<LocationDto> locationDtos = locationConverter.entityToDto(locationPage.getContent());
        return new PageImpl<>(locationDtos, pageable, locationPage.getTotalElements());
    }

    @Override
    public Page<LocationDto> findAllWarehouses(Pageable pageable) {
        logger.info("Find all warehouses");
        UserDto userDto = userService.getCurrentUser();
        Page<Location> warehousePage = locationRepository.findAllByCustomer_IdAndLocationTypeAndStatus(pageable,
                userDto.getCustomer()
                       .getId(),
                LocationType.WAREHOUSE,
                DeletedStatus.ACTIVE);
        List<LocationDto> locationDtos = locationConverter.entityToDto(warehousePage.getContent());
        return new PageImpl<>(locationDtos, pageable, warehousePage.getTotalElements());
    }

    @Override
    public Page<LocationDto> findAllShops(Pageable pageable) {
        logger.info("Find all shops");
        UserDto userDto = userService.getCurrentUser();
        Page<Location> shopPage = locationRepository.findAllByCustomer_IdAndLocationTypeAndStatus(pageable,
                userDto.getCustomer()
                       .getId(),
                LocationType.SHOP,
                DeletedStatus.ACTIVE);
        List<LocationDto> locationDtos = locationConverter.entityToDto(shopPage.getContent());
        return new PageImpl<>(locationDtos, pageable, shopPage.getTotalElements());
    }

    @Override
    public LocationDto create(LocationDto locationDto) throws BusinessException {
        logger.info("Create location");
        UserDto userDto = userService.getCurrentUser();
        locationDto.setCustomer(userDto.getCustomer());
        Location location = locationConverter.dtoToEntity(locationDto);
        if (identifierExists(location.getIdentifier())) {
            logger.error("Not unique identifier {}", location.getIdentifier());
            throw new BusinessException("Identifier should be unique");
        }
        Location persistLocation = locationRepository.save(location);
        return locationConverter.entityToDto(persistLocation);
    }

    @Override
    public LocationDto update(LocationDto locationDto) throws BusinessException {
        logger.info("Update location");
        UserDto userDto = userService.getCurrentUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        Location location = locationConverter.dtoToEntity(locationDto);
        Location persistLocation = locationRepository.findByIdAndCustomer_IdAndStatus(location.getId(), customerId, DeletedStatus.ACTIVE)
                                                     .orElse(new Location());
        if (!location.getIdentifier()
                     .equals(persistLocation.getIdentifier())) {
            if (identifierExists(location.getIdentifier())) {
                logger.error("Not unique identifier {}", location.getIdentifier());
                throw new BusinessException("Identifier should be unique");
            }
        }
        persistLocation.setAddress(location.getAddress());
        persistLocation.setLocationTax(location.getLocationTax());
        persistLocation.setAvailableCapacity(location.getAvailableCapacity());
        persistLocation.setCustomer(location.getCustomer());
        persistLocation.setIdentifier(location.getIdentifier());
        persistLocation.setLocationType(location.getLocationType());
        persistLocation.setTotalCapacity(location.getTotalCapacity());
        persistLocation = locationRepository.save(persistLocation);
        return locationConverter.entityToDto(persistLocation);
    }

    @Override
    public List<LocationDto> delete(List<Long> locationIds) {
        logger.info("Delete location");
        UserDto userDto = userService.getCurrentUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        List<Location> locations = locationRepository.findAllByIdInAndCustomer_Id(locationIds, customerId);
        List<Location> undeletedLocations = new ArrayList<>(locations);
        for (Location location : locations) {
            if (userRepository.findAllByLocation_IdAndUserStatus(location.getId(), Status.ACTIVE)
                              .size() == 0) {
                location.setStatus(DeletedStatus.DELETED);
                locationRepository.save(location);
                undeletedLocations.remove(location);
            }
        }
        return locationConverter.entityToDto(undeletedLocations);
    }

    @Override
    public boolean identifierExists(String identifier) {
        logger.info("Check for existing location identifier {}", identifier);
        UserDto userDto = userService.getCurrentUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        return locationRepository.findAllByIdentifierIgnoreCaseAndCustomer_Id(identifier, customerId)
                                 .size() != 0;
    }
}
