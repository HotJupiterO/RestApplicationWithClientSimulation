package com.application.project3.controllers;

import com.application.project3.dto.MeasurementDTO;
import com.application.project3.models.Measurement;
import com.application.project3.models.Sensor;
import com.application.project3.services.MeasurementService;
import com.application.project3.services.SensorService;
import com.application.project3.util.ErrorResponse;
import com.application.project3.util.ErrorUtil;
import com.application.project3.util.MeasurementValidator;
import com.application.project3.util.WorkingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/measurement")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {
        Measurement measurement = toMeasurement(measurementDTO);
        measurementValidator.validate(measurement, bindingResult);
        if (bindingResult.hasErrors()) {
            ErrorUtil.errorToUser(bindingResult);
        }
        Optional<Sensor> sensor = sensorService.findByName(measurement.getSensor().getName());
        if (sensor.isPresent()) {
            measurement.setSensor(sensor.get());
            measurementService.add(measurement);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(WorkingException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    @ResponseBody
    public List<MeasurementDTO> measurementDTOS() {
        return measurementService.getAllMeasurements()
                .stream()
                .map(this::toDTO)
                .toList();
    }


    @GetMapping("/rainyDaysCount")
    @ResponseBody
    public Long rainingDaysCount() {
        return measurementService.getAllMeasurements()
                .stream()
                .filter(Measurement::isRaining)
                .count();
    }

    /*---------------UTIL------------------------*/
    private MeasurementDTO toDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private Measurement toMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }
}
