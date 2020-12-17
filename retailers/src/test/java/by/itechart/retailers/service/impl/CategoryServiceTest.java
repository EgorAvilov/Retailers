package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CategoryConverter;
import by.itechart.retailers.dto.CategoryDto;
import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.Category;
import by.itechart.retailers.entity.Customer;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.CategoryRepository;
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
public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryConverter categoryConverter;

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    public void findAllTest() {
        //given
        Long customerId = 1L;
        List<Category> categories = new ArrayList<Category>() {{
            add(new Category());
        }};
        List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>() {{
            add(new CategoryDto());
        }};
        PageRequest pageable = PageRequest.of(0, 1);
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, 1);
        CustomerDto customerDto = CustomerDto.builder()
                                             .id(customerId)
                                             .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customerDto)
                                 .build();
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(categoryRepository.findAllByCustomer_Id(pageable, customerId)).thenReturn(categoryPage);
        when(categoryConverter.entityToDto(categories)).thenReturn(categoryDtos);
        //when
        categoryService.findAll(pageable);
        //then
        verify(categoryRepository).findAllByCustomer_Id(pageable, customerId);
        verify(categoryConverter).entityToDto(categories);
    }

    @Test
    public void findByIdTest() {
        //given
        Long customerId = 1L;
        CustomerDto customer = CustomerDto.builder()
                                          .id(customerId)
                                          .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customer)
                                 .build();
        Category category = new Category();
        CategoryDto categoryDto = new CategoryDto();
        Long categoryId = 1L;
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(categoryRepository.findByIdAndCustomer_Id(categoryId, customerId)).thenReturn(Optional.of(category));
        when(categoryConverter.entityToDto(category)).thenReturn(categoryDto);
        //when
        categoryService.findById(categoryId);
        //then
        verify(categoryRepository).findByIdAndCustomer_Id(categoryId, customerId);
        verify(categoryConverter).entityToDto(category);
    }


    @Test(expected = BusinessException.class)
    public void createTestBusinessExceptionName() {
        //given
        CategoryDto categoryDto = CategoryDto.builder()
                                             .name("name")
                                             .build();
        Category category = Category.builder()
                                    .name("name")
                                    .build();
        Long customerId = 1L;
        CustomerDto customer = CustomerDto.builder()
                                          .id(customerId)
                                          .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customer)
                                 .build();
        List<Category> categories = new ArrayList<Category>() {{
            add(category);
        }};
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(categoryRepository.findAllByNameIgnoreCaseAndCustomer_Id(category.getName(), customerId)).thenReturn(categories);
        //when
        categoryService.create(categoryDto);
        //then
        verify(categoryConverter).dtoToEntity(categoryDto);
    }

    @Test
    public void createTest() {
        //given
        CategoryDto categoryDto = CategoryDto.builder()
                                             .name("name")
                                             .build();
        Category category = Category.builder()
                                    .name("name")
                                    .build();
        Long customerId = 1L;
        CustomerDto customer = CustomerDto.builder()
                                          .id(customerId)
                                          .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customer)
                                 .build();
        when(categoryConverter.dtoToEntity(categoryDto)).thenReturn(category);
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(categoryRepository.findAllByNameIgnoreCaseAndCustomer_Id(category.getName(), customerId)).thenReturn(new ArrayList<>());
        //when
        categoryService.create(categoryDto);
        //then
        verify(categoryConverter).dtoToEntity(categoryDto);
        verify(categoryRepository).save(category);
    }

    @Test(expected = BusinessException.class)
    public void updateTestBusinessExceptionName() {
        Long customerId = 1L;
        CustomerDto customerDto = CustomerDto.builder()
                                             .id(customerId)
                                             .build();
        Customer customer = Customer.builder()
                                    .id(customerId)
                                    .build();
        CategoryDto categoryDto = CategoryDto.builder()
                                             .id(1L)
                                             .name("name")
                                             .customer(customerDto)
                                             .build();
        Category category = Category.builder()
                                    .id(1L)
                                    .name("name")
                                    .customer(customer)
                                    .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customerDto)
                                 .build();
        List<Category> categories = new ArrayList<Category>() {{
            add(category);
        }};
        when(categoryConverter.dtoToEntity(categoryDto)).thenReturn(category);
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(categoryRepository.findAllByNameIgnoreCaseAndCustomer_Id(category.getName(), customerId)).thenReturn(categories);
        //when
        categoryService.update(categoryDto);
        //then
        verify(categoryConverter).dtoToEntity(categoryDto);
    }

    @Test
    public void updateTest() {
        Long customerId = 1L;
        Long categoryId=1L;
        CustomerDto customerDto = CustomerDto.builder()
                                             .id(customerId)
                                             .build();
        Customer customer = Customer.builder()
                                    .id(customerId)
                                    .build();
        CategoryDto categoryDto = CategoryDto.builder()
                                             .id(categoryId)
                                             .name("name")
                                             .customer(customerDto)
                                             .build();
        Category category = Category.builder()
                                    .id(categoryId)
                                    .name("name")
                                    .customer(customer)
                                    .build();
        UserDto userDto = UserDto.builder()
                                 .customer(customerDto)
                                 .build();
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(categoryConverter.dtoToEntity(categoryDto)).thenReturn(category);
        when(categoryRepository.findByIdAndCustomer_Id(categoryId,customerId)).thenReturn(Optional.of(category));
        //when
        categoryService.update(categoryDto);
        //then
        verify(categoryConverter).dtoToEntity(categoryDto);
        verify(categoryRepository).findByIdAndCustomer_Id(categoryId,customerId);
        verify(categoryRepository).save(category);
    }
}

