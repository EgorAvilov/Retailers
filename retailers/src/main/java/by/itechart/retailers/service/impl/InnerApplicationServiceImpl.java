package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.InnerApplicationConverter;
import by.itechart.retailers.converter.UserConverter;
import by.itechart.retailers.dto.InnerApplicationDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.*;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.InnerApplicationRepository;
import by.itechart.retailers.repository.LocationProductRepository;
import by.itechart.retailers.repository.LocationRepository;
import by.itechart.retailers.service.interfaces.InnerApplicationService;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InnerApplicationServiceImpl implements InnerApplicationService {

    private final InnerApplicationRepository innerApplicationRepository;
    private final InnerApplicationConverter innerApplicationConverter;
    private final UserConverter userConverter;
    private final UserService userService;
    private final LocationProductRepository locationProductRepository;
    private final LocationRepository locationRepository;
    Logger logger = LoggerFactory.getLogger(InnerApplicationServiceImpl.class);

    @Autowired
    public InnerApplicationServiceImpl(InnerApplicationRepository innerApplicationRepository, InnerApplicationConverter innerApplicationConverter, UserConverter userConverter, UserService userService, LocationProductRepository locationProductRepository, LocationRepository locationRepository) {
        this.innerApplicationRepository = innerApplicationRepository;
        this.innerApplicationConverter = innerApplicationConverter;
        this.userConverter = userConverter;
        this.userService = userService;
        this.locationProductRepository = locationProductRepository;
        this.locationRepository = locationRepository;
    }


    @Override
    public InnerApplicationDto findById(long innerApplicationId) {
        logger.info("Find inner application by id {}", innerApplicationId);
        UserDto userDto = userService.getCurrentUser();
        Long destinationLocationId = userDto.getLocation()
                                            .getId();
        InnerApplication innerApplication = innerApplicationRepository.findByIdAndDestinationLocation_Id(innerApplicationId, destinationLocationId)
                                                                      .orElse(new InnerApplication());
        return innerApplicationConverter.entityToDto(innerApplication);
    }

    @Override
    public Page<InnerApplicationDto> findAll(Pageable pageable) {
        logger.info("Find all inner applications");
        UserDto userDto = userService.getCurrentUser();
        Page<InnerApplication> innerApplicationPage = innerApplicationRepository.findAllByDestinationLocation_Id(pageable, userDto.getLocation()
                                                                                                                                  .getId());
        List<InnerApplicationDto> innerApplicationDtos = innerApplicationConverter.entityToDto(innerApplicationPage.getContent());
        return new PageImpl<>(innerApplicationDtos, pageable, innerApplicationPage.getTotalElements());

    }

    @Override
    @Transactional
    public InnerApplicationDto create(InnerApplicationDto innerApplicationDto) throws BusinessException {
        logger.info("Create inner application");
        UserDto userDto = userService.getCurrentUser();
        innerApplicationDto.setCreator(userDto);
        InnerApplication innerApplication = innerApplicationConverter.dtoToEntity(innerApplicationDto);
        Location location = locationRepository.findById(innerApplication.getSourceLocation()
                                                                        .getId())
                                              .orElse(new Location());

        if (applicationNumberExists(innerApplication.getApplicationNumber())) {
            logger.error("Not unique number {}", innerApplication.getApplicationNumber());
            throw new BusinessException("Application number should be unique");
        }
        List<ApplicationRecord> applicationRecords = innerApplication.getRecordsList();
        for (ApplicationRecord applicationRecord : applicationRecords) {
            Long productId = applicationRecord.getProduct()
                                              .getId();
            LocationProduct locationProduct = locationProductRepository.findByLocation_IdAndProduct_Id(location.getId(), productId);
            if (applicationRecord.getAmount() > locationProduct.getAmount()) {
                logger.error("Not enough product {} in location", locationProduct.getProduct()
                                                                                 .getId());
                throw new BusinessException("Not enough amount of " + locationProduct.getAmount() + " in location " + locationProduct.getLocation()
                                                                                                                                     .getIdentifier());
            }
            Integer availableCapacity = location.getAvailableCapacity();
            location.setAvailableCapacity(availableCapacity + applicationRecord.getAmount() * applicationRecord.getProduct()
                                                                                                               .getVolume());
            Integer amount = locationProduct.getAmount();
            locationProduct.setAmount(amount - applicationRecord.getAmount());
            locationProductRepository.save(locationProduct);
            innerApplication.setSourceLocation(location);
        }
        InnerApplication persistInnerApplication = innerApplicationRepository.save(innerApplication);
        return innerApplicationConverter.entityToDto(persistInnerApplication);
    }

    @Override
    public InnerApplicationDto update(InnerApplicationDto innerApplicationDto) {
        logger.info("Update inner application");
        UserDto userDto = userService.getCurrentUser();
        Long destinationLocationId = userDto.getLocation()
                                            .getId();
        innerApplicationDto.setUpdater(userDto);
        InnerApplication innerApplication = innerApplicationConverter.dtoToEntity(innerApplicationDto);
        InnerApplication persistInnerApplication = innerApplicationRepository.findByIdAndDestinationLocation_Id(innerApplicationDto.getId(), destinationLocationId)
                                                                             .orElse(new InnerApplication());
        persistInnerApplication.setDestinationLocation(innerApplication.getDestinationLocation());
        persistInnerApplication = innerApplicationRepository.save(persistInnerApplication);
        return innerApplicationConverter.entityToDto(persistInnerApplication);
    }

    @Override
    public InnerApplicationDto updateStatus(Long innerApplicationId) throws BusinessException {
        logger.info("Update inner application status {}", innerApplicationId);
        UserDto userDto = userService.getCurrentUser();
        Long destinationLocationId = userDto.getLocation()
                                            .getId();
        InnerApplication innerApplication = innerApplicationRepository.findByIdAndDestinationLocation_Id(innerApplicationId, destinationLocationId)
                                                                      .orElse(new InnerApplication());
        Location location = innerApplication.getDestinationLocation();
        Integer totalUnitAmount = innerApplication.getTotalUnitNumber();
        if (totalUnitAmount > location.getAvailableCapacity()) {
            throw new BusinessException("Not enough space in destination location");
        }
        List<ApplicationRecord> applicationRecords = innerApplication.getRecordsList();
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
        innerApplication.setDestinationLocation(location);
        innerApplication.setApplicationStatus(ApplicationStatus.FINISHED_PROCESSING);
        innerApplication.setUpdater(userConverter.dtoToEntity(userDto));
        innerApplication.setUpdatingDateTime(LocalDateTime.now());
        innerApplicationRepository.save(innerApplication);
        return innerApplicationConverter.entityToDto(innerApplication);
    }

    @Override
    public boolean applicationNumberExists(Integer applicationNumber) {
        logger.info("Check for existing inner application number {}", applicationNumber);
        UserDto userDto = userService.getCurrentUser();
        User user = userConverter.dtoToEntity(userDto);
        return innerApplicationRepository.findAllByApplicationNumberAndCreator(applicationNumber, user)
                                         .size() != 0;
    }
}
