package by.itechart.retailers.controller;

import by.itechart.retailers.dto.InnerApplicationDto;
import by.itechart.retailers.service.interfaces.InnerApplicationService;
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
@RequestMapping(URL_API + URL_INNER_APPLICATIONS)
public class InnerApplicationController {

    private final InnerApplicationService innerApplicationService;

    @Autowired
    public InnerApplicationController(InnerApplicationService innerApplicationService) {
        this.innerApplicationService = innerApplicationService;
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault(sort = {"applicationStatus", "registrationDateTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(innerApplicationService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = INNER_APPLICATION_ID_VALUE)
    public ResponseEntity findById(@PathVariable(name = INNER_APPLICATION_ID) Long innerApplicationId) {
        return new ResponseEntity<>(innerApplicationService.findById(innerApplicationId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid InnerApplicationDto innerApplicationDto) {
        return new ResponseEntity<>(innerApplicationService.create(innerApplicationDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid InnerApplicationDto innerApplicationDto) {
        return new ResponseEntity<>(innerApplicationService.update(innerApplicationDto), HttpStatus.OK);
    }

    @PutMapping(value = INNER_APPLICATION_STATUS_VALUE)
    public ResponseEntity updateStatus(@RequestBody Long innerApplicationId) {
        return new ResponseEntity<>(innerApplicationService.updateStatus(innerApplicationId), HttpStatus.OK);
    }
}
