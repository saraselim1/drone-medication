package com.drone.medication.util;

import com.drone.medication.bootstrap.CustomStatics;
import com.drone.medication.core.payload.request.AddingDroneRequest;
import com.drone.medication.core.payload.request.MedicationDetails;
import lombok.experimental.UtilityClass;

import javax.validation.ValidationException;
import java.util.List;

@UtilityClass
public class DroneValidationUtil {

    public static void validateDroneRequest(AddingDroneRequest addingDroneRequest) {
        if(CustomStatics.REGISTER_DRONES.containsKey(addingDroneRequest.getSerialNumber())){
            throw new ValidationException("Serial number exists");
        }
    }

    public static void validateDroneExistence(String serialNumber) {
        if (!CustomStatics.REGISTER_DRONES.containsKey(serialNumber)) {
            throw new ValidationException("Serial number not found");
        }
    }

    public static void validateDroneWeightLimit(String serialNumber, List<MedicationDetails> medicationCodes) {

        Double totalWeight = Double.valueOf(0);
        Double droneWeight = CustomStatics.REGISTER_DRONES.get(serialNumber).getWeightLimit();

        for (MedicationDetails med: medicationCodes) {
            double medTotalWeight = CustomStatics.REGISTER_MEDICATIONS.get(med.getCode()).getWeight() * med.getQuantity();
            totalWeight += medTotalWeight;
        }

        if(totalWeight>droneWeight){
            throw new ValidationException("Total weigh of medications greater that drone weight Limit.");
        }
    }

    public static void validateDroneBattery(String serialNumber) {
        if (CustomStatics.REGISTER_DRONES.get(serialNumber).getBatteryCapacity()<25) {
            throw new ValidationException("Drone battery is not changed");
        }
    }
}
