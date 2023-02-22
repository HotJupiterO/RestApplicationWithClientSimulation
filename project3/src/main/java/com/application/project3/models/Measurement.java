package com.application.project3.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "value")
    @Min(-100)
    @Max(100)
    @NotNull
    private Double value;
    @Column(name = "raining")
    @NotNull
    private Boolean raining;
    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    @NotNull(message = "Can't be null")
    private Sensor sensor;
    @Column(name = "measure_time")
    @NotNull
    private LocalDateTime measureTime;

    public Boolean isRaining() {
        return raining.equals(true);
    }
}
