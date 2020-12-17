package by.itechart.retailers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryDto {

    private Long id;

    @NotBlank(message = "Amount field can't be empty.")
    @Size(min = 3, max = 30, message = "Category can be from 3 to 30 symbols.")
    private String name;

    @NotBlank(message = "Category tax can't be empty.")
    @DecimalMin(value = "0", message = "Tax must be equals or greater than 0.")
    private BigDecimal categoryTax;

    private CustomerDto customer;
}
