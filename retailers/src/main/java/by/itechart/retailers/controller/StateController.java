package by.itechart.retailers.controller;

import by.itechart.retailers.dto.StateDto;
import by.itechart.retailers.service.interfaces.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static by.itechart.retailers.constant.UrlConstants.*;

@RestController
@RequestMapping(URL_API + URL_STATES)
public class StateController {

    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(stateService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = STATE_ID_VALUE)
    public ResponseEntity findById(@PathVariable(name = STATE_ID) Long stateId) {
        return new ResponseEntity<>(stateService.findById(stateId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody StateDto stateDto) {
        return new ResponseEntity<>(stateService.create(stateDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody StateDto stateDto) {
        return new ResponseEntity<>(stateService.update(stateDto), HttpStatus.OK);
    }
}
