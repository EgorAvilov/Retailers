package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDto {

    private Long id;

    @NotNull(message = "Customer name can't be empty.")
    @Size(min = 4, max = 40, message = "Customer name can be from 4 to 40 symbols.")
    private String name;

    @Email(message = "Wrong email format.")
    private String email;

    @PastOrPresent(message = "Registration date can't be in the future.")
    private LocalDate registrationDate;

    private Status customerStatus;
}
