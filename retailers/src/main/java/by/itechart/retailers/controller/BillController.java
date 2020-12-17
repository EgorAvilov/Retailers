package by.itechart.retailers.controller;

import by.itechart.retailers.dto.BillDto;
import by.itechart.retailers.service.interfaces.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static by.itechart.retailers.constant.UrlConstants.*;

@RestController
@RequestMapping(URL_API + URL_BILLS)
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping(value =BILL_FIND_ALL_BY_CUSTOMER_VALUE)
    public ResponseEntity findAllByCustomer(@PageableDefault(sort = "registrationDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(billService.findAllByCustomer(pageable), HttpStatus.OK);
    }

    @GetMapping(value = BILL_FIND_ALL_BY_LOCATION_VALUE)
    public ResponseEntity findAllByLocation(@PageableDefault(sort = "registrationDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(billService.findAllByLocation(pageable), HttpStatus.OK);
    }
    @GetMapping(value = BILL_ID_VALUE)
    public ResponseEntity findById(@PathVariable(name = BILL_ID) Long billId) {
        return new ResponseEntity<>(billService.findById(billId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid BillDto billDto) {
        return new ResponseEntity<>(billService.create(billDto), HttpStatus.CREATED);
    }
}
