package com.drone.medication.controller.v1;


import com.drone.medication.core.domain.DroneMedicationDetails;
import com.drone.medication.core.domain.Medication;
import com.drone.medication.core.service.MedicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaTypeEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/medications")
@AllArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @GetMapping( produces = "application/json")
    public ResponseEntity<List<Medication>> getAllMedications() {
        return new ResponseEntity<>(medicationService.getAllMedications(), HttpStatus.OK);
    }


}
