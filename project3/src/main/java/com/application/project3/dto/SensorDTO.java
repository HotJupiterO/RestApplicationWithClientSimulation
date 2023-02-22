package com.application.project3.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorDTO {
    @NotEmpty
    @Size(min = 3, max = 30, message = "Should contain at least 3 and less than 30 characters")
    private String name;
}