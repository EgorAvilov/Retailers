package by.itechart.retailers.dto;

import by.itechart.retailers.entity.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InnerApplicationDto {

    private Long id;

    @Min(value = 0, message = "Wrong application number.")
    private Integer applicationNumber;

    private LocationDto sourceLocation;

    private LocationDto destinationLocation;

    private UserDto creator;

    private UserDto updater;

    @PastOrPresent(message = "Registration date of application can't be in the future.")
    private LocalDateTime registrationDateTime;

    @PastOrPresent(message = "Updating date of application can't be in the future.")
    private LocalDateTime updatingDateTime;

    private ApplicationStatus applicationStatus;

    private List<ApplicationRecordDto> recordsList;

    @Min(value = 0, message = "Product amount must be equals or greater than 0.")
    private Integer totalProductAmount;

    @Min(value = 0, message = "Unit number must be equals or greater than 0.")
    private Integer totalUnitNumber;
}
