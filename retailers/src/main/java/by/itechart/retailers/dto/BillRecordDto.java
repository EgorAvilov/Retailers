package by.itechart.retailers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BillRecordDto {

    private Long id;

    private ProductDto product;

    @Min(value = 1, message = "Product amount must be greater than 0.")
    private Integer productAmount;

    @NotBlank(message = "Price can't be empty.")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0.")
    private BigDecimal productPrice;
}
