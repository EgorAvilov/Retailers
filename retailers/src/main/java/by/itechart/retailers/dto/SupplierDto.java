package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SupplierDto {

    private Long id;

    @NotBlank(message = "Name can't be empty.")
    @Size(min = 3, max = 40, message = "Name can be from 3 to 40 symbols.")
    private String fullName;

    @Min(value = 1, message = "Amount must be equals or greater than 0.")
    @Max(value = 999999999, message = "Amount must be equals or greater than 0.")
    private Integer identifier;

    private List<SupplierWarehouseDto> wareHouseList;

    private CustomerDto customer;

    private Status supplierStatus;
}
