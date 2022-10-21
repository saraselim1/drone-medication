package com.drone.medication.core.service;

import com.drone.medication.core.domain.Drone;
import com.drone.medication.core.domain.DroneMedicationDetails;
import com.drone.medication.core.domain.Medication;
import com.drone.medication.core.payload.request.AddingDroneRequest;
import com.drone.medication.core.payload.request.LoadMedicationRequest;
import com.drone.medication.core.payload.response.DroneBatteryResponse;

import java.util.List;

public interface DroneService {

    Drone addNewDrone(AddingDroneRequest addingDroneRequest);

    void loadDroneWithMedications(String serialNumber, LoadMedicationRequest loadMedicationRequest);

    List<DroneMedicationDetails> getLoadedMedications(String serialNumber);

    List<Drone> getAvailableDrones();

    DroneBatteryResponse getDroneBattery(String serialNumber);
}
