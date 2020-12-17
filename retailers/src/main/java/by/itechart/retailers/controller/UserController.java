package by.itechart.retailers.controller;

import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.service.interfaces.UserService;
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
@RequestMapping(URL_API + URL_USERS)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault(sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = USER_ID_VALUE)
    public ResponseEntity findById(@PathVariable(name = USER_ID) Long userId) {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userService.create(userDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userService.update(userDto), HttpStatus.OK);
    }

    @PutMapping(value = USER_PASSWORD_VALUE)
    public ResponseEntity updatePassword(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userService.updatePassword(userDto), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity updateStatus(@RequestBody List<Long> userIds) {
        return new ResponseEntity<>(userService.updateStatus(userIds), HttpStatus.OK);
    }
}
