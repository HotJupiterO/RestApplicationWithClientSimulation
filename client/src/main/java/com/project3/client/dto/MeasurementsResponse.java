package com.project3.client.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MeasurementsResponse {
    private List<MeasurementDTO> measurements;
}
