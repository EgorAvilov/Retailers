package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.WriteOffActConverter;
import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.dto.WriteOffActDto;
import by.itechart.retailers.dto.WriteOffActRecordDto;
import by.itechart.retailers.entity.*;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.LocationProductRepository;
import by.itechart.retailers.repository.LocationRepository;
import by.itechart.retailers.repository.WriteOffActRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class WriteOffActServiceTest {

    @Mock
    WriteOffActConverter writeOffActConverter;

    @Mock
    WriteOffActRepository writeOffActRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    LocationProductRepository locationProductRepository;

    @Mock
    LocationRepository locationRepository;

    @InjectMocks
    WriteOffActServiceImpl writeOffActService;

    @Test
    public void findAllByCustomerTest() {
        //given
        Long customerId = 1L;
        List<WriteOffAct> writeOffActs = new ArrayList<>();
        List<WriteOffActDto> writeOffActDtos = new ArrayList<>();
        PageRequest pageable = PageRequest.of(0, 1);
        Page<WriteOffAct> writeOffActPage = new PageImpl<>(writeOffActs, pageable, 1);
        List<Location> locations = new ArrayList<>();
        CustomerDto customerDto = CustomerDto.builder()
                                             .id(1L)
                                             .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customerDto)
                                 .build();
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(locationRepository.findAllByCustomer_Id(customerId)).thenReturn(locations);
        when(writeOffActRepository.findAllByLocationIn(pageable, locations)).thenReturn(writeOffActPage);
        when(writeOffActConverter.entityToDto(writeOffActPage.getContent())).thenReturn(writeOffActDtos);
        //when
        writeOffActService.findAllByCustomer(pageable);
        //then
        verify(userService).getCurrentUser();
        verify(locationRepository).findAllByCustomer_Id(customerId);
        verify(writeOffActRepository).findAllByLocationIn(pageable, locations);
        verify(writeOffActConverter).entityToDto(writeOffActPage.getContent());
    }

    @Test
    public void findByIdTest() {
        //given
        WriteOffAct writeOffAct = new WriteOffAct();
        WriteOffActDto writeOffActDto = new WriteOffActDto();
        Long writeOffActId = 1L;
        Long customerId = 1L;
        CustomerDto customer = CustomerDto.builder()
                                          .id(customerId)
                                          .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customer)
                                 .build();
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(writeOffActRepository.findByIdAndCustomer_Id(writeOffActId,customerId)).thenReturn(Optional.of(writeOffAct));
        when(writeOffActConverter.entityToDto(writeOffAct)).thenReturn(writeOffActDto);
        //when
        writeOffActService.findById(writeOffActId);
        //then
        verify(writeOffActRepository).findByIdAndCustomer_Id(writeOffActId, customerId);
        verify(writeOffActConverter).entityToDto(writeOffAct);
    }

    @Test(expected = BusinessException.class)
    public void createTestBusinessExceptionBillNumber() {
        //given
        Long customerId = 1L;
        CustomerDto customer = CustomerDto.builder()
                                          .id(customerId)
                                          .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customer)
                                 .build();
        List<WriteOffActRecordDto> writeOffActDtos = new ArrayList<>();
        WriteOffActDto writeOffActDto = WriteOffActDto.builder()
                                                      .writeOffActNumber(1)
                                                      .writeOffActRecords(writeOffActDtos)
                                                      .build();
        List<WriteOffActRecord> writeOffActRecords = new ArrayList<>();
        WriteOffAct writeOffAct = WriteOffAct.builder()
                                             .writeOffActNumber(1)
                                             .writeOffActRecords(writeOffActRecords)
                                             .build();
        List<WriteOffAct> writeOffActs = new ArrayList<WriteOffAct>() {{
            add(writeOffAct);
        }};
        when(writeOffActConverter.dtoToEntity(writeOffActDto)).thenReturn(writeOffAct);
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(writeOffActRepository.findAllByWriteOffActNumberAndCustomer_Id(writeOffAct.getWriteOffActNumber(), customerId)).thenReturn(writeOffActs);
        //when
        writeOffActService.create(writeOffActDto);
        //than
        verify(writeOffActConverter).dtoToEntity(writeOffActDto);
        verify(userService).getCurrentUser();
        verify(writeOffActRepository).findAllByWriteOffActNumberAndCustomer_Id(writeOffAct.getWriteOffActNumber(), customerId);
    }

    @Test(expected = BusinessException.class)
    public void createTestBusinessExceptionProductAmount() {
        //given
        Long customerId = 1L;
        CustomerDto customer = CustomerDto.builder()
                                          .id(customerId)
                                          .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customer)
                                 .build();
        Product product = Product.builder()
                                 .id(1L)
                                 .label("label")
                                 .build();
        List<WriteOffActRecordDto> writeOffActRecordDtos = new ArrayList<>();
        WriteOffActDto writeOffActDto = WriteOffActDto.builder()
                                                      .writeOffActNumber(1)
                                                      .writeOffActRecords(writeOffActRecordDtos)
                                                      .build();
        Location location = Location.builder()
                                    .id(1L)
                                    .build();
        WriteOffActRecord writeOffActRecord = WriteOffActRecord.builder()
                                                               .amount(1)
                                                               .product(product)
                                                               .build();
        List<WriteOffActRecord> writeOffActRecords = new ArrayList<WriteOffActRecord>() {{
            add(writeOffActRecord);
        }};
        WriteOffAct writeOffAct = WriteOffAct.builder()
                                             .writeOffActNumber(1)
                                             .writeOffActRecords(writeOffActRecords)
                                             .location(location)
                                             .build();
        LocationProduct locationProduct = LocationProduct.builder()
                                                         .amount(0)
                                                         .product(product)
                                                         .build();
        when(writeOffActConverter.dtoToEntity(writeOffActDto)).thenReturn(writeOffAct);
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(writeOffActRepository.findAllByWriteOffActNumberAndCustomer_Id(writeOffAct.getWriteOffActNumber(), customerId)).thenReturn(new ArrayList<>());
        when(locationProductRepository.findByLocation_IdAndProduct_Id(location.getId(), writeOffActRecord.getProduct()
                                                                                                         .getId())).thenReturn(locationProduct);
        assertThat(writeOffActRecords.get(0)
                                     .getAmount() > locationProduct.getAmount());
        //when
        writeOffActService.create(writeOffActDto);
        //than
        verify(writeOffActConverter).dtoToEntity(writeOffActDto);
    }

    @Test
    public void createTest() {
        //given
        List<WriteOffActRecordDto> writeOffActRecordDtos = new ArrayList<>();
        Long customerId = 1L;
        CustomerDto customer = CustomerDto.builder()
                                          .id(customerId)
                                          .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customer)
                                 .build();
        Location location = Location.builder()
                                    .availableCapacity(1)
                                    .id(1L)
                                    .totalCapacity(1)
                                    .build();
        Product product = Product.builder()
                                 .id(1L)
                                 .label("label")
                                 .volume(1)
                                 .build();
        WriteOffActRecord writeOffActRecord = WriteOffActRecord.builder()
                                                               .amount(0)
                                                               .product(product)
                                                               .build();
        List<WriteOffActRecord> writeOffActRecords = new ArrayList<WriteOffActRecord>() {{
            add(writeOffActRecord);
        }};
        WriteOffActDto writeOffActDto = WriteOffActDto.builder()
                                                      .writeOffActNumber(1)
                                                      .writeOffActRecords(writeOffActRecordDtos)
                                                      .build();
        WriteOffAct writeOffAct = WriteOffAct.builder()
                                             .writeOffActNumber(1)
                                             .writeOffActRecords(writeOffActRecords)
                                             .location(location)
                                             .build();
        LocationProduct locationProduct = LocationProduct.builder()
                                                         .amount(0)
                                                         .product(product)
                                                         .build();
        when(writeOffActConverter.dtoToEntity(writeOffActDto)).thenReturn(writeOffAct);
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(writeOffActRepository.findAllByWriteOffActNumberAndCustomer_Id(writeOffAct.getWriteOffActNumber(), customerId)).thenReturn(new ArrayList<>());
        when(locationProductRepository.findByLocation_IdAndProduct_Id(location.getId(), writeOffActRecord.getProduct()
                                                                                                         .getId())).thenReturn(locationProduct);
        assertThat(writeOffActRecords.get(0)
                                     .getAmount() > locationProduct.getAmount());
        //when
        writeOffActService.create(writeOffActDto);
        //than
        verify(writeOffActConverter).dtoToEntity(writeOffActDto);
    }
}

