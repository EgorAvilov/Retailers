package by.itechart.retailers.controller;

import by.itechart.retailers.dto.ProductDto;
import by.itechart.retailers.service.interfaces.ProductService;
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
@RequestMapping(URL_API + URL_PRODUCTS)
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault(sort = "label", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(productService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = PRODUCT_ID_VALUE)
    public ResponseEntity findById(@PathVariable(name = PRODUCT_ID) Long productId) {
        return new ResponseEntity<>(productService.findById(productId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid ProductDto productDto) {
        return new ResponseEntity<>(productService.create(productDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid ProductDto productDto) {
        return new ResponseEntity<>(productService.update(productDto), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody List<Long> productIds) {
        return new ResponseEntity<>(productService.delete(productIds), HttpStatus.OK);
    }
}
