package com.drone.medication.core.service;

import com.drone.medication.bootstrap.CustomStatics;
import com.drone.medication.core.domain.Medication;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicationServiceImpl implements MedicationService{

    @Override
    public List<Medication> getAllMedications() {
        return CustomStatics.REGISTER_MEDICATIONS.values().stream().collect(Collectors.toList());
    }
}
