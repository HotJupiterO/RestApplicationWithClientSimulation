package com.application.project3.controllers;

import com.application.project3.dto.SensorDTO;
import com.application.project3.models.Sensor;
import com.application.project3.services.SensorService;
import com.application.project3.util.ErrorUtil;
import com.application.project3.util.WorkingException;
import com.application.project3.util.ErrorResponse;
import com.application.project3.util.SensorValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
@AllArgsConstructor
public class SensorsController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerTheSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                        BindingResult bindingResult) {
        Sensor sensor = toSensor(sensorDTO);
        sensorValidator.validate(sensor, bindingResult);
        if (bindingResult.hasErrors()) {
            ErrorUtil.errorToUser(bindingResult);
        }
        sensorService.save(toSensor(sensorDTO));
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

    private Sensor toSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

}
