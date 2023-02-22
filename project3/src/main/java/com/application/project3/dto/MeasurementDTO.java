package com.application.project3.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementDTO {
    @Min(value = -100)
    @Max(value = 100)
    @NotNull
    private Double value;
    @NotNull
    private Boolean raining;
    @NotNull
    private SensorDTO sensor;
}
