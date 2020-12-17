package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.SupplierApplicationConverter;
import by.itechart.retailers.converter.UserConverter;
import by.itechart.retailers.dto.SupplierApplicationDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.*;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.LocationProductRepository;
import by.itechart.retailers.repository.LocationRepository;
import by.itechart.retailers.repository.SupplierApplicationRepository;
import by.itechart.retailers.service.interfaces.SupplierApplicationService;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupplierApplicationServiceImpl implements SupplierApplicationService {

    private final SupplierApplicationRepository supplierApplicationRepository;
    private final LocationProductRepository locationProductRepository;
    private final LocationRepository locationRepository;
    private final SupplierApplicationConverter supplierApplicationConverter;
    private final UserConverter userConverter;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(SupplierApplicationServiceImpl.class);

    @Autowired
    public SupplierApplicationServiceImpl(SupplierApplicationRepository supplierApplicationRepository, LocationProductRepository locationProductRepository, LocationRepository locationRepository, SupplierApplicationConverter supplierApplicationConverter, UserConverter userConverter, UserService userService) {
        this.supplierApplicationRepository = supplierApplicationRepository;
        this.locationProductRepository = locationProductRepository;
        this.locationRepository = locationRepository;
        this.supplierApplicationConverter = supplierApplicationConverter;
        this.userConverter = userConverter;
        this.userService = userService;
    }

    @Override
    public SupplierApplicationDto findById(long supplierApplicationId) {
        logger.info("Find supplier application by id {}", supplierApplicationId);
        UserDto userDto = userService.getCurrentUser();
        Long destinationLocationId = userDto.getLocation()
                                            .getId();
        SupplierApplication supplierApplication = supplierApplicationRepository.findByIdAndDestinationLocation_Id(supplierApplicationId, destinationLocationId)
                                                                               .orElse(new SupplierApplication());
        return supplierApplicationConverter.entityToDto(supplierApplication);
    }

    @Override
    public Page<SupplierApplicationDto> findAll(Pageable pageable) {
        logger.info("Find all supplier applications");
        UserDto userDto = userService.getCurrentUser();
        Page<SupplierApplication> supplierApplicationPage = supplierApplicationRepository.findAllByDestinationLocation_Id(pageable, userDto.getLocation()
                                                                                                                                           .getId());
        List<SupplierApplicationDto> supplierApplicationDtos = supplierApplicationConverter.entityToDto(supplierApplicationPage.getContent());
        return new PageImpl<>(supplierApplicationDtos, pageable, supplierApplicationPage.getTotalElements());
    }

    @Override
    public SupplierApplicationDto create(SupplierApplicationDto supplierApplicationDto) throws BusinessException {
        logger.info("Create supplier application");
        UserDto userDto = userService.getCurrentUser();
        supplierApplicationDto.setCreator(userDto);
        supplierApplicationDto.setDestinationLocation(userDto.getLocation());
        SupplierApplication supplierApplication = supplierApplicationConverter.dtoToEntity(supplierApplicationDto);
        if (applicationNumberExists(supplierApplication.getApplicationNumber())) {
            logger.error("Not unique number {}", supplierApplication.getApplicationNumber());
            throw new BusinessException("Application number should be unique");
        }
        SupplierApplication persistSupplierApplication = supplierApplicationRepository.save(supplierApplication);
        return supplierApplicationConverter.entityToDto(persistSupplierApplication);
    }

    @Override
    public SupplierApplicationDto update(SupplierApplicationDto supplierApplicationDto) {
        logger.info("Update supplier application");
        SupplierApplication supplierApplication = supplierApplicationConverter.dtoToEntity(supplierApplicationDto);
        SupplierApplication persistSupplierApplication = supplierApplicationRepository
                .findById(supplierApplication.getId())
                .orElse(new SupplierApplication());
        persistSupplierApplication.setDestinationLocation(supplierApplication.getDestinationLocation());
        persistSupplierApplication = supplierApplicationRepository.save(persistSupplierApplication);
        return supplierApplicationConverter.entityToDto(persistSupplierApplication);
    }

    @Override
    public SupplierApplicationDto updateStatus(Long supplierApplicationId) throws BusinessException {
        logger.info("Update supplier application status");
        UserDto userDto = userService.getCurrentUser();
        Long destinationLocation = userDto.getLocation()
                                          .getId();
        SupplierApplication supplierApplication = supplierApplicationRepository.findByIdAndDestinationLocation_Id(supplierApplicationId, destinationLocation)
                                                                               .orElse(new SupplierApplication());
        Location location = locationRepository.findById(supplierApplication.getDestinationLocation().getId()).orElse(new Location());
        Integer totalUnitAmount = supplierApplication.getTotalUnitNumber();
        if (totalUnitAmount > location.getAvailableCapacity()) {
            logger.error("Not enough space in location {}", location.getId());
            throw new BusinessException("Not enough space in destination location");
        }
        List<ApplicationRecord> applicationRecords = supplierApplication.getRecordsList();
        for (ApplicationRecord applicationRecord : applicationRecords) {
            LocationProduct locationProduct = locationProductRepository.findByLocation_IdAndProduct_Id(location.getId(), applicationRecord.getProduct()
                                                                                                                                          .getId());
            if (locationProduct != null) {
                if (locationProduct.getAmount() == 0) {
                    locationProduct.setCost(applicationRecord.getCost());
                    locationProduct.setAmount(applicationRecord.getAmount());
                    locationProductRepository.save(locationProduct);
                } else {
                    if (locationProduct.getCost()
                                       .compareTo(applicationRecord.getCost()) < 0) {
                        locationProduct.setCost(applicationRecord.getCost());
                        Integer amount = locationProduct.getAmount();
                        locationProduct.setAmount(amount + applicationRecord.getAmount());
                    }else{
                        Integer amount = locationProduct.getAmount();
                        locationProduct.setAmount(amount + applicationRecord.getAmount());
                    }
                    locationProductRepository.save(locationProduct);
                }
            } else {
                locationProduct = new LocationProduct();
                locationProduct.setProduct(applicationRecord.getProduct());
                locationProduct.setCost(applicationRecord.getCost());
                locationProduct.setAmount(applicationRecord.getAmount());
                locationProduct.setLocation(location);
                locationProductRepository.save(locationProduct);
            }
        }
        Integer availableCapacity = location.getAvailableCapacity();
        location.setAvailableCapacity(availableCapacity - totalUnitAmount);
        supplierApplication.setDestinationLocation(location);
        supplierApplication.setApplicationStatus(ApplicationStatus.FINISHED_PROCESSING);
        supplierApplication.setUpdater(userConverter.dtoToEntity(userDto));
        supplierApplication.setUpdatingDateTime(LocalDateTime.now());
        supplierApplicationRepository.save(supplierApplication);
        return supplierApplicationConverter.entityToDto(supplierApplication);
    }

    @Override
    public boolean applicationNumberExists(Integer applicationNumber) {
        logger.info("Check for existing supplier application number {}", applicationNumber);
        UserDto userDto = userService.getCurrentUser();
        User user = userConverter.dtoToEntity(userDto);
        return supplierApplicationRepository.findAllByApplicationNumberAndCreator(applicationNumber, user)
                                            .size() != 0;
    }
}
