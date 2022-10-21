package com.drone.medication.util;

import com.drone.medication.bootstrap.CustomStatics;
import com.drone.medication.core.payload.request.MedicationDetails;
import lombok.experimental.UtilityClass;

import javax.validation.ValidationException;
import java.util.List;

@UtilityClass
public class MedicationValidationUtil {

    public static void validateMedicationExistence(List<MedicationDetails> medicationCodes) {
        for (MedicationDetails med: medicationCodes) {
            if(!CustomStatics.REGISTER_MEDICATIONS.containsKey(med.getCode())){
                throw new ValidationException("Medication code: "+ med+ " not exist.");
            }
        }
    }
}
