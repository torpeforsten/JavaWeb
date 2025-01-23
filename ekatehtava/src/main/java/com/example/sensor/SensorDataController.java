package com.example.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensor")
public class SensorDataController {

    @Autowired
    private SensorDataRepository repository;

    // 2. Hae kaikki tiedot
    @GetMapping
    public List<SensorData> getAllSensorData() {
        return repository.findAll();
    }

    // 3. Tallenna uudet tiedot
    @PostMapping
    public SensorData createSensorData(@RequestBody SensorData sensorData) {
        return repository.save(sensorData);
    }

    // 4. Muokkaa tietoja
    @PutMapping("/{id}")
    public ResponseEntity<SensorData> updateSensorData(@PathVariable Long id, @RequestBody SensorData sensorData) {
        Optional<SensorData> existingData = repository.findById(id);
        if (existingData.isPresent()) {
            SensorData updatedData = existingData.get();
            updatedData.setUnit(sensorData.getUnit());
            updatedData.setAmount(sensorData.getAmount());
            updatedData.setLocation(sensorData.getLocation());
            updatedData.setTimestamp(sensorData.getTimestamp());
            repository.save(updatedData);
            return ResponseEntity.ok(updatedData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Poista yksittäinen tieto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensorData(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}