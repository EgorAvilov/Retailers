package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.LocationConverter;
import by.itechart.retailers.dto.LocationDto;
import by.itechart.retailers.entity.Location;
import by.itechart.retailers.repository.LocationRepository;
import by.itechart.retailers.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;
    private LocationConverter locationConverter;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository,
                               LocationConverter locationConverter) {
        this.locationRepository = locationRepository;
        this.locationConverter = locationConverter;
    }

    @Override
    public LocationDto findById(long locationProductId) {
        Location location = locationRepository.findById(locationProductId).orElse(new Location());

        return locationConverter.entityToDto(location);
    }

    @Override
    public List<LocationDto> findAll() {
        List<Location> locationList = locationRepository.findAll();

        return locationConverter.entityToDto(locationList);
    }

    @Override
    public LocationDto create(LocationDto locationDto) {
        Location location = locationConverter.dtoToEntity(locationDto);
        Location persistLocation = locationRepository.save(location);

        return locationConverter.entityToDto(persistLocation);
    }

    @Override
    public LocationDto update(LocationDto locationDto) {
        Location location = locationConverter.dtoToEntity(locationDto);
        Location persistLocation = locationRepository.findById(location.getId()).orElse(new Location());

        persistLocation.setAddress(location.getAddress());
        persistLocation.setAvailableCapacity(location.getAvailableCapacity());
        persistLocation.setCustomer(location.getCustomer());
        persistLocation.setIdentifier(location.getIdentifier());
        persistLocation.setProductList(location.getProductList());
        persistLocation.setWriteOffActList(location.getWriteOffActList());
        persistLocation.setLocationType(location.getLocationType());
        persistLocation.setTotalCapacity(location.getTotalCapacity());

        return locationConverter.entityToDto(persistLocation);
    }
}
