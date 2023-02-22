package com.application.project3.services;

import com.application.project3.models.Sensor;
import com.application.project3.repositories.SensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }


    public Optional<Sensor> findByName(String name) {
       return sensorRepository.findSensorByName(name); //todo (throw RuntimeException::new)
    }



}
