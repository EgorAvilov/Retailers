package by.itechart.retailers.dto;


import by.itechart.retailers.entity.DeletedStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SupplierWarehouseDto {

    private Long id;

    @NotBlank(message = "Name can't be empty.")
    @Size(min = 1, max = 20, message = "Name can be from 1 to 20 symbols.")
    private String name;

    private AddressDto address;

    private DeletedStatus status;
}