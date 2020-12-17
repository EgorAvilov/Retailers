package by.itechart.retailers.dto;

import by.itechart.retailers.annotation.Upc;
import by.itechart.retailers.entity.DeletedStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDto {

    private Long id;

    @Upc
    private Long upc;

    @NotBlank(message = "Label can't be empty.")
    @Size(min = 3, max = 30, message = "Label can be from 3 to 30 symbols.")
    private String label;

    private CategoryDto category;

    @Min(value = 1, message = "Product volume must be greater than 0.")
    private Integer volume;

    private CustomerDto customer;

    private DeletedStatus status;
}
