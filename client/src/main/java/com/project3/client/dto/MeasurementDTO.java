package com.project3.client.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementDTO {
    private Double value;
    private Boolean raining;
    private SensorDTO sensor;
}
