package by.itechart.retailers.controller;

import by.itechart.retailers.dto.LocationProductDto;
import by.itechart.retailers.service.interfaces.LocationProductService;
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
@RequestMapping(URL_API + URL_LOCATION_PRODUCTS)
public class LocationProductController {

    private final LocationProductService locationProductService;

    @Autowired
    public LocationProductController(LocationProductService locationProductService) {
        this.locationProductService = locationProductService;
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault(sort = "cost", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(locationProductService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = LOCATION_PRODUCT_ID_VALUE)
    public ResponseEntity findById(@PathVariable(name = LOCATION_PRODUCT_ID) Long locationProductId) {
        return new ResponseEntity<>(locationProductService.findById(locationProductId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid LocationProductDto locationProductDto) {
        return new ResponseEntity<>(locationProductService.create(locationProductDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid LocationProductDto locationProductDto) {
        return new ResponseEntity<>(locationProductService.update(locationProductDto), HttpStatus.OK);
    }
}
