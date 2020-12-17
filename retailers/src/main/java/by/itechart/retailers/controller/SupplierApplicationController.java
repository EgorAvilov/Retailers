package by.itechart.retailers.controller;

import by.itechart.retailers.dto.SupplierApplicationDto;
import by.itechart.retailers.service.interfaces.SupplierApplicationService;
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
@RequestMapping(URL_API + URL_SUPPLIER_APPLICATIONS)
public class SupplierApplicationController {

    private final SupplierApplicationService supplierApplicationService;

    @Autowired
    public SupplierApplicationController(SupplierApplicationService supplierApplicationService) {
        this.supplierApplicationService = supplierApplicationService;
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault(sort = {"applicationStatus", "registrationDateTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(supplierApplicationService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = SUPPLIER_APPLICATION_ID_VALUE)
    public ResponseEntity findById(@PathVariable(name = SUPPLIER_APPLICATION_ID) Long supplierApplicationId) {
        return new ResponseEntity<>(supplierApplicationService.findById(supplierApplicationId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid SupplierApplicationDto supplierApplicationDto) {
        return new ResponseEntity<>(supplierApplicationService.create(supplierApplicationDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid SupplierApplicationDto supplierApplicationDto) {
        return new ResponseEntity<>(supplierApplicationService.update(supplierApplicationDto), HttpStatus.OK);
    }

    @PutMapping(value = SUPPLIER_APPLICATION_STATUS_VALUE)
    public ResponseEntity updateStatus(@RequestBody Long supplierApplicationId) {
        return new ResponseEntity<>(supplierApplicationService.updateStatus(supplierApplicationId), HttpStatus.OK);
    }
}
