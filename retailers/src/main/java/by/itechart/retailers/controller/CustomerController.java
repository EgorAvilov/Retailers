package by.itechart.retailers.controller;

import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.service.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static by.itechart.retailers.constant.UrlConstants.*;

@RestController
@RequestMapping(URL_API + URL_CUSTOMERS)
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(customerService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = CUSTOMER_ID_VALUE)
    public ResponseEntity findById(@PathVariable(name = CUSTOMER_ID) Long customerId) {
        return new ResponseEntity<>(customerService.findById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.create(customerDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.update(customerDto), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity updateStatus(@RequestBody List<Long> customerIds) {
        return new ResponseEntity<>(customerService.updateStatus(customerIds), HttpStatus.OK);
    }
}
