package by.itechart.retailers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressDto {

    private Long id;

    private StateDto state;

    @NotBlank(message = "City is a mandatory field.")
    @Size(min = 3, max = 30, message = "City can be from 3 to 30 symbols.")
    private String city;

    @NotBlank(message = "Address is a mandatory field.")
    @Size(min = 5, max = 30, message = "Address can be from 5 to 30 symbols.")
    private String firstAddressLine;

    private String secondAddressLine;
}
