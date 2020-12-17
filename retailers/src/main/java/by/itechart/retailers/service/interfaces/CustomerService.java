package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    CustomerDto findById(long customerId);

    Page<CustomerDto> findAll(Pageable pageable);

    CustomerDto create(CustomerDto customerDto) throws BusinessException;

    CustomerDto update(CustomerDto customerDto) throws BusinessException;

    List<CustomerDto> updateStatus(List<Long> customerIds);

    boolean emailExists(String email);
}
