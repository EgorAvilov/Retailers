package by.itechart.retailers.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHelper {

    @ExceptionHandler(value = {NotUniqueEmailException.class})
    public ResponseEntity<Object> handleNotUniqueEmailException(NotUniqueEmailException ex) {
        log.error("Email should be unique: ", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }
}