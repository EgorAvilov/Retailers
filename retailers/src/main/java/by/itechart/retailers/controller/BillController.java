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

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault(sort = "billNumber", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(billService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{billId}")
    public ResponseEntity findById(@PathVariable(name = "billId") Long billId) {
        return new ResponseEntity<>(billService.findById(billId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody BillDto billDto) {
        return new ResponseEntity<>(billService.create(billDto), HttpStatus.CREATED);
    }
}
