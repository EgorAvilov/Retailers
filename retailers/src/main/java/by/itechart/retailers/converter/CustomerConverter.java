package by.itechart.retailers.converter;

import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerConverter {

    public CustomerDto entityToDto(Customer customer) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(customer, CustomerDto.class);
    }

    public Customer dtoToEntity(CustomerDto customerDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(customerDto, Customer.class);
    }

    public List<CustomerDto> entityToDto(List<Customer> customers) {
        return customers.stream()
                        .map(this::entityToDto)
                        .collect(Collectors.toList());
    }

    public List<Customer> dtoToEntity(List<CustomerDto> customerDtos) {
        return customerDtos.stream()
                           .map(this::dtoToEntity)
                           .collect(Collectors.toList());
    }
}
