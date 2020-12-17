package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.entity.Customer;
import by.itechart.retailers.entity.Status;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.CustomerRepository;
import by.itechart.retailers.service.interfaces.CustomerService;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerConverter customerConverter,
                               UserService userService) {
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
        this.userService = userService;
    }

    @Override
    public CustomerDto findById(long customerId) {
        logger.info("Find customer by id {}", customerId);
        Customer customer = customerRepository.findById(customerId)
                                              .orElse(new Customer());
        return customerConverter.entityToDto(customer);
    }

    @Override
    public Page<CustomerDto> findAll(Pageable pageable) {
        logger.info("Find all customers");
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        List<CustomerDto> customerDtos = customerConverter.entityToDto(customerPage.getContent());
        return new PageImpl<>(customerDtos, pageable, customerPage.getTotalElements());
    }

    @Override
    @Transactional
    public CustomerDto create(CustomerDto customerDto) throws BusinessException {
        logger.info("Create customer");
        if (emailExists(customerDto.getEmail())) {
            logger.error("Not unique email {}", customerDto.getEmail());
            throw new BusinessException("Email should be unique");
        }
        Customer customer = customerConverter.dtoToEntity(customerDto);
        Customer persistsCustomer = customerRepository.save(customer);
        userService.create(customerConverter.entityToDto(persistsCustomer));
        return customerConverter.entityToDto(persistsCustomer);
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) throws BusinessException {
        logger.info("Update customer");
        Customer customer = customerConverter.dtoToEntity(customerDto);
        Customer persistCustomer = customerRepository.findById(customer.getId())
                                                     .orElse(new Customer());
        if (!customer.getEmail().equals(persistCustomer.getEmail())) {
            if (emailExists(customer.getEmail())) {
                logger.error("Not unique email {}", customer.getEmail());
                throw new BusinessException("Email should be unique");
            }
        }

        persistCustomer.setName(customer.getName());
        persistCustomer.setEmail(customer.getEmail());
        persistCustomer = customerRepository.save(persistCustomer);
        return customerConverter.entityToDto(persistCustomer);
    }

    @Override
    public List<CustomerDto> updateStatus(List<Long> customerIds) {
        logger.info("Update customer status");
        List<Customer> customers = customerRepository.findAllById(customerIds);
        for (Customer customer : customers) {
            if (customer.getCustomerStatus()
                        .equals(Status.ACTIVE)) {
                customer.setCustomerStatus(Status.DISABLED);
            } else {
                customer.setCustomerStatus(Status.ACTIVE);
            }
            customerRepository.save(customer);
        }
        return customerConverter.entityToDto(customers);
    }

    @Override
    public boolean emailExists(String email) {
        logger.info("Check for existing customer email {}", email);
        return customerRepository.findAllByEmailIgnoreCase(email)
                                 .size() != 0;
    }
}
