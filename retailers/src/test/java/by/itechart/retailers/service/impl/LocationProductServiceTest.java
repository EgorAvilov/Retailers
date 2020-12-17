package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.LocationProductConverter;
import by.itechart.retailers.dto.LocationDto;
import by.itechart.retailers.dto.LocationProductDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.Location;
import by.itechart.retailers.entity.LocationProduct;
import by.itechart.retailers.repository.LocationProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LocationProductServiceTest {

    @Mock
    LocationProductRepository locationProductRepository;

    @Mock
    LocationProductConverter locationProductConverter;

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    LocationProductServiceImpl locationProductService;

    @Test
    public void findAllTest() {
        //given
        Long locationId = 1L;
        LocationDto location = LocationDto.builder()
                                          .id(1L)
                                          .build();
        UserDto userDto = UserDto.builder()
                                 .location(location)
                                 .build();
        List<LocationProduct> locationProducts = new ArrayList<LocationProduct>() {{
            add(new LocationProduct());
        }};
        List<LocationProductDto> locationProductDtos = new ArrayList<LocationProductDto>() {{
            add(new LocationProductDto());
        }};
        PageRequest pageable = PageRequest.of(0, 1);
        Page<LocationProduct> locationProductPage = new PageImpl<>(locationProducts, pageable, 1);
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(locationProductRepository.findAllByLocation_IdAndAmountGreaterThan(pageable, locationId, 0)).thenReturn(locationProductPage);
        when(locationProductConverter.entityToDto(locationProducts)).thenReturn(locationProductDtos);
        //when
        locationProductService.findAll(pageable);
        //then
        verify(locationProductRepository).findAllByLocation_IdAndAmountGreaterThan(pageable, locationId, 0);
        verify(locationProductConverter).entityToDto(locationProducts);
    }

    @Test
    public void findByIdTest() {
        //given
        LocationProduct locationProduct = new LocationProduct();
        LocationProductDto locationProductDto = new LocationProductDto();
        Long locationProductId = 1L;
        Long locationId = 1L;
        LocationDto locationDto = LocationDto.builder()
                                             .id(locationId)
                                             .build();
        UserDto userDto = UserDto.builder()
                                 .location(locationDto)
                                 .build();
        when(userService.getCurrentUser()).thenReturn(userDto);

        when(locationProductRepository.findByIdAndLocation_IdAndAmountGreaterThan(locationProductId, locationId, 0)).thenReturn(Optional.of(locationProduct));
        when(locationProductConverter.entityToDto(locationProduct)).thenReturn(locationProductDto);
        //when
        locationProductService.findById(locationProductId);
        //then
        verify(locationProductRepository).findByIdAndLocation_IdAndAmountGreaterThan(locationProductId, locationId, 0);
        verify(locationProductConverter).entityToDto(locationProduct);
    }

    @Test
    public void createTest() {
        //given
        Long locationId = 1L;
        Location location = Location.builder()
                                    .id(locationId)
                                    .build();
        LocationProduct locationProduct = LocationProduct.builder()
                                                         .id(1L)
                                                         .location(location)
                                                         .build();
        LocationDto locationDto = LocationDto.builder()
                                             .id(locationId)
                                             .build();
        LocationProductDto locationProductDto = LocationProductDto.builder()
                                                                  .id(1L)
                                                                  .location(locationDto)
                                                                  .build();
        UserDto userDto = UserDto.builder()
                                 .location(locationDto)
                                 .build();
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(locationProductConverter.dtoToEntity(locationProductDto)).thenReturn(locationProduct);
        //when
        locationProductService.create(locationProductDto);
        //then
        verify(locationProductConverter).dtoToEntity(locationProductDto);
        verify(locationProductRepository).save(locationProduct);
    }

    @Test
    public void updateTest() {
        //given
        Long locationId = 1L;
        Long locationProductId=1L;
        Location location = Location.builder()
                                    .id(locationId)
                                    .build();
        LocationProduct locationProduct = LocationProduct.builder()
                                                         .id(locationProductId)
                                                         .location(location)
                                                         .build();
        LocationDto locationDto = LocationDto.builder()
                                             .id(locationId)
                                             .build();
        LocationProductDto locationProductDto = LocationProductDto.builder()
                                                                  .id(1L)
                                                                  .location(locationDto)
                                                                  .build();
        UserDto userDto = UserDto.builder()
                                 .location(locationDto)
                                 .build();
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(locationProductConverter.dtoToEntity(locationProductDto)).thenReturn(locationProduct);
        when(locationProductRepository.findByIdAndLocation_Id(locationId,locationProductId)).thenReturn(Optional.of(locationProduct));
        //when
        locationProductService.update(locationProductDto);
        //then
        verify(locationProductConverter).dtoToEntity(locationProductDto);
        verify(locationProductRepository).findByIdAndLocation_Id(locationId,locationProductId);
        verify(locationProductRepository).save(locationProduct);
    }
}

