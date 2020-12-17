package by.itechart.retailers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {

    HttpStatus httpStatus;

    private List<String> errors;
}
