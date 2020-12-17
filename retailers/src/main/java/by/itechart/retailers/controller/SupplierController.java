package by.itechart.retailers.controller;

import by.itechart.retailers.dto.SupplierDto;
import by.itechart.retailers.service.interfaces.SupplierService;
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
@RequestMapping(URL_API + URL_SUPPLIERS)
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault(sort = "fullName", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(supplierService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = SUPPLIER_ID_VALUE)
    public ResponseEntity findById(@PathVariable(name = SUPPLIER_ID) Long supplierId) {
        return new ResponseEntity<>(supplierService.findById(supplierId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid SupplierDto supplierDto) {
        return new ResponseEntity<>(supplierService.create(supplierDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid SupplierDto supplierDto) {
        return new ResponseEntity<>(supplierService.update(supplierDto), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity updateStatus(@RequestBody List<Long> supplierIdsList) {
        return new ResponseEntity<>(supplierService.updateStatus(supplierIdsList), HttpStatus.OK);
    }
}
