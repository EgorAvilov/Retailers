package by.itechart.retailers.converter;

import by.itechart.retailers.dto.LocationProductDto;
import by.itechart.retailers.entity.LocationProduct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationProductConverter {
    public LocationProductDto entityToDto(LocationProduct locationProduct) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(locationProduct, LocationProductDto.class);
    }

    public LocationProduct dtoToEntity(LocationProductDto locationProductDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(locationProductDto, LocationProduct.class);
    }

    public List<LocationProductDto> entityToDto(List<LocationProduct> locations) {
        return locations.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<LocationProduct> dtoToEntity(List<LocationProductDto> locationDtos) {
        return locationDtos.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
