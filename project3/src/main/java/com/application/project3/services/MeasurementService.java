package com.application.project3.services;

import com.application.project3.models.Measurement;
import com.application.project3.repositories.MeasurementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    @Transactional
    public void add(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setMeasureTime(LocalDateTime.now());
    }

    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }
}
