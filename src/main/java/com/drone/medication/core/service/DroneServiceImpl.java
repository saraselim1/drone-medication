package com.drone.medication.core.service;

import com.drone.medication.bootstrap.CustomStatics;
import com.drone.medication.common.enums.StateEnum;
import com.drone.medication.core.domain.Drone;
import com.drone.medication.core.domain.DroneMedicationDetails;
import com.drone.medication.core.payload.request.AddingDroneRequest;
import com.drone.medication.core.payload.request.LoadMedicationRequest;
import com.drone.medication.core.payload.request.MedicationDetails;
import com.drone.medication.core.payload.response.DroneBatteryResponse;
import com.drone.medication.util.DroneUtil;
import com.drone.medication.util.DroneValidationUtil;
import com.drone.medication.util.MedicationValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DroneServiceImpl implements DroneService {


    @Override
    public Drone addNewDrone(AddingDroneRequest addingDroneRequest) {

        DroneValidationUtil.validateDroneRequest(addingDroneRequest);
        Drone drone = DroneUtil.buildDrone(addingDroneRequest);
        CustomStatics.REGISTER_DRONES.put(addingDroneRequest.getSerialNumber(), drone);
        return drone;
    }

    @Override
    public void loadDroneWithMedications(String serialNumber, LoadMedicationRequest loadMedicationRequest) {

        DroneValidationUtil.validateDroneExistence(serialNumber);
        DroneValidationUtil.validateDroneBattery(serialNumber);

        MedicationValidationUtil.validateMedicationExistence(loadMedicationRequest.getMedications());
        DroneValidationUtil.validateDroneWeightLimit(serialNumber,loadMedicationRequest.getMedications());
        updateDrone(serialNumber, loadMedicationRequest);
    }

    @Override
    public List<DroneMedicationDetails> getLoadedMedications(String serialNumber) {

        DroneValidationUtil.validateDroneExistence(serialNumber);
        return CustomStatics.REGISTER_DRONES.get(serialNumber).getMedicationList();

    }

    @Override
    public List<Drone> getAvailableDrones() {

        List<Drone> availableDrones = new ArrayList<>();
        for (Map.Entry<String, Drone> entry : CustomStatics.REGISTER_DRONES.entrySet()) {
            if (entry.getValue().getState().equals(StateEnum.IDLE)
                    &&entry.getValue().getBatteryCapacity()>25) {
                availableDrones.add(entry.getValue());
            }
        }
        return availableDrones;

    }

    @Override
    public DroneBatteryResponse getDroneBattery(String serialNumber) {
        DroneValidationUtil.validateDroneExistence(serialNumber);
        return DroneBatteryResponse.builder()
                .serialNumber(serialNumber)
                .batteryPercentage(CustomStatics.REGISTER_DRONES.get(serialNumber).getBatteryCapacity())
                .build();

    }

    private void updateDrone(String serialNumber, LoadMedicationRequest loadMedicationRequest) {

        CustomStatics.REGISTER_DRONES.get(serialNumber).setState(StateEnum.LOADED);
        CustomStatics.REGISTER_DRONES.get(serialNumber).setMedicationList(new ArrayList<>());

        for (MedicationDetails med: loadMedicationRequest.getMedications()) {

            DroneMedicationDetails droneMedicationDetails =
                    DroneMedicationDetails.builder()
                            .quantity(med.getQuantity())
                            .medication(CustomStatics.REGISTER_MEDICATIONS.get(med.getCode()))
                            .build();
            CustomStatics.REGISTER_DRONES.get(serialNumber).getMedicationList().add(droneMedicationDetails);
        }
    }
}
