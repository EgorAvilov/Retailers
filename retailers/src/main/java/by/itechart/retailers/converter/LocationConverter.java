package by.itechart.retailers.converter;

import by.itechart.retailers.dto.LocationDto;
import by.itechart.retailers.entity.Location;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationConverter {
    public LocationDto entityToDto(Location location) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(location, LocationDto.class);
    }

    public Location dtoToEntity(LocationDto locationDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(locationDto, Location.class);
    }

    public List<LocationDto> entityToDto(List<Location> locations) {
        return locations.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<Location> dtoToEntity(List<LocationDto> locationDtos) {
        return locationDtos.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
