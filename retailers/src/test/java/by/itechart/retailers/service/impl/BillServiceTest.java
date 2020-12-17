package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.BillConverter;
import by.itechart.retailers.dto.*;
import by.itechart.retailers.entity.*;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.BillRepository;
import by.itechart.retailers.repository.LocationProductRepository;
import by.itechart.retailers.repository.LocationRepository;
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
public class BillServiceTest {

    @Mock
    BillConverter billConverter;

    @Mock
    BillRepository billRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    LocationProductRepository locationProductRepository;

    @Mock
    LocationRepository locationRepository;

    @InjectMocks
    BillServiceImpl billService;

    @Test
    public void findAllByCustomerTest() {
        //given
        Long customerId = 1L;
        List<Bill> bills = new ArrayList<>();
        List<BillDto> billDtos = new ArrayList<>();
        PageRequest pageable = PageRequest.of(0, 1);
        Page<Bill> billPage = new PageImpl<>(bills, pageable, 1);
        List<Location> locations = new ArrayList<>();
        CustomerDto customerDto = CustomerDto.builder()
                                             .id(1L)
                                             .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customerDto)
                                 .build();

        when(userService.getCurrentUser()).thenReturn(userDto);
        when(locationRepository.findAllByCustomer_Id(customerId)).thenReturn(locations);
        when(billRepository.findAllByLocationIn(pageable, locations)).thenReturn(billPage);
        when(billConverter.entityToDto(billPage.getContent())).thenReturn(billDtos);
        //when
        billService.findAllByCustomer(pageable);
        //then
        verify(userService).getCurrentUser();
        verify(locationRepository).findAllByCustomer_Id(customerId);
        verify(billRepository).findAllByLocationIn(pageable, locations);
        verify(billConverter).entityToDto(billPage.getContent());
    }

    @Test
    public void findAllByLocationTest() {
        //given
        Long customerId = 1L;
        Long locationId = 1L;
        LocationDto locationDto = LocationDto.builder()
                                             .id(locationId)
                                             .build();
        List<Bill> bills = new ArrayList<>();
        List<BillDto> billDtos = new ArrayList<>();
        PageRequest pageable = PageRequest.of(0, 1);
        Page<Bill> billPage = new PageImpl<>(bills, pageable, 1);
        List<Location> locations = new ArrayList<>();
        CustomerDto customerDto = CustomerDto.builder()
                                             .id(1L)
                                             .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customerDto)
                                 .location(locationDto)
                                 .build();

        when(userService.getCurrentUser()).thenReturn(userDto);
        when(billRepository.findAllByLocation_Id(pageable, locationId)).thenReturn(billPage);
        when(billConverter.entityToDto(billPage.getContent())).thenReturn(billDtos);
        //when
        billService.findAllByLocation(pageable);
        //then
        verify(userService).getCurrentUser();
        verify(billRepository).findAllByLocation_Id(pageable, locationId);
        verify(billConverter).entityToDto(billPage.getContent());
    }

    @Test
    public void findByIdTest() {
        //given
        Bill bill = new Bill();
        BillDto billDto = new BillDto();
        Long billId = 1L;
        Long customerId = 1L;
        CustomerDto customerDto = CustomerDto.builder()
                                             .id(customerId)
                                             .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customerDto)
                                 .build();
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(billRepository.findByIdAndCustomer_Id(billId, customerId)).thenReturn(Optional.of(bill));
        when(billConverter.entityToDto(bill)).thenReturn(billDto);
        //when
        billService.findById(billId);
        //then
        verify(billRepository).findByIdAndCustomer_Id(billId, customerId);
        verify(billConverter).entityToDto(bill);
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
        List<BillRecordDto> billRecordDtos = new ArrayList<>();
        BillDto billDto = BillDto.builder()
                                 .billNumber(1)
                                 .recordList(billRecordDtos)
                                 .build();
        List<BillRecord> billRecords = new ArrayList<>();
        Bill bill = Bill.builder()
                        .billNumber(1)
                        .recordList(billRecords)
                        .build();
        List<Bill> bills = new ArrayList<Bill>() {{
            add(bill);
        }};
        when(billConverter.dtoToEntity(billDto)).thenReturn(bill);
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(billRepository.findAllByBillNumberAndCustomer_Id(bill.getBillNumber(), customerId)).thenReturn(bills);
        //when
        billService.create(billDto);
        //than
        verify(billConverter).dtoToEntity(billDto);
        verify(userService).getCurrentUser();
        verify(billRepository).findAllByBillNumberAndCustomer_Id(bill.getBillNumber(), customerId);
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
        List<BillRecordDto> billRecordDtos = new ArrayList<>();
        BillDto billDto = BillDto.builder()
                                 .billNumber(1)
                                 .recordList(billRecordDtos)
                                 .build();

        Location location = Location.builder()
                                    .id(1L)
                                    .build();

        BillRecord billRecord = BillRecord.builder()
                                          .productAmount(1)
                                          .product(product)
                                          .build();

        List<BillRecord> billRecords = new ArrayList<BillRecord>() {{
            add(billRecord);
        }};
        Bill bill = Bill.builder()
                        .billNumber(1)
                        .recordList(billRecords)
                        .location(location)
                        .build();
        LocationProduct locationProduct = LocationProduct.builder()
                                                         .amount(0)
                                                         .product(product)
                                                         .build();
        when(billConverter.dtoToEntity(billDto)).thenReturn(bill);
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(billRepository.findAllByBillNumberAndCustomer_Id(bill.getBillNumber(), customerId)).thenReturn(new ArrayList<>());
        when(locationProductRepository.findByLocation_IdAndProduct_Id(location.getId(), billRecord.getProduct()
                                                                                                  .getId())).thenReturn(locationProduct);
        assertThat(billRecords.get(0)
                              .getProductAmount() > locationProduct.getAmount());
        //when
        billService.create(billDto);
        //than
        verify(billConverter).dtoToEntity(billDto);
    }

    @Test
    public void createTest() {
        //given
        List<BillRecordDto> billRecordDtos = new ArrayList<>();

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
        BillRecord billRecord = BillRecord.builder()
                                          .productAmount(0)
                                          .product(product)
                                          .build();
        List<BillRecord> billRecords = new ArrayList<BillRecord>() {{
            add(billRecord);
        }};
        BillDto billDto = BillDto.builder()
                                 .billNumber(1)
                                 .recordList(billRecordDtos)
                                 .build();
        Bill bill = Bill.builder()
                        .billNumber(1)
                        .recordList(billRecords)
                        .location(location)
                        .build();
        LocationProduct locationProduct = LocationProduct.builder()
                                                         .amount(0)
                                                         .product(product)
                                                         .build();
        when(billConverter.dtoToEntity(billDto)).thenReturn(bill);
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(billRepository.findAllByBillNumberAndCustomer_Id(bill.getBillNumber(), customerId)).thenReturn(new ArrayList<>());
        when(locationProductRepository.findByLocation_IdAndProduct_Id(location.getId(), billRecord.getProduct()
                                                                                                  .getId())).thenReturn(locationProduct);
        assertThat(billRecords.get(0)
                              .getProductAmount() > locationProduct.getAmount());
        //when
        billService.create(billDto);
        //than
        verify(billConverter).dtoToEntity(billDto);
    }
}
