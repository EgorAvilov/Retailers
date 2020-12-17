package by.itechart.retailers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequestDto {

    @NotBlank(message = "Email can't be empty.")
    String email;

    @NotBlank(message = "Password can't be empty.")
    String password;

}
