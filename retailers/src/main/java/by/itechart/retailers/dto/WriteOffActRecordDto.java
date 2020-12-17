package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Reason;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WriteOffActRecordDto {

    private Long id;

    private ProductDto product;

    @Min(value = 1, message = "Product amount must be equals or greater than 1.")
    private Integer amount;

    private Reason reason;
}
