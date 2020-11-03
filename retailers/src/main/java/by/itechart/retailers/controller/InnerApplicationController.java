package by.itechart.retailers.controller;

import by.itechart.retailers.dto.InnerApplicationDto;
import by.itechart.retailers.service.InnerApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/innerApplications")
public class InnerApplicationController {

    private InnerApplicationService innerApplicationService;

    @Autowired
    public InnerApplicationController(InnerApplicationService innerApplicationService) {
        this.innerApplicationService = innerApplicationService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(innerApplicationService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{innerApplicationId}")
    public ResponseEntity findById(@PathVariable(name = "innerApplicationId") Long innerApplicationId) {
        return new ResponseEntity<>(innerApplicationService.findById(innerApplicationId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody InnerApplicationDto innerApplicationDto) {
        return new ResponseEntity<>(innerApplicationService.create(innerApplicationDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody InnerApplicationDto innerApplicationDto) {
        return new ResponseEntity<>(innerApplicationService.update(innerApplicationDto), HttpStatus.OK);
    }


}
