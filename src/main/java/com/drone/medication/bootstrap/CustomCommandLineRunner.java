package com.drone.medication.bootstrap;

import com.drone.medication.core.domain.Medication;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CustomCommandLineRunner  implements CommandLineRunner {
    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String[] args) {
        buildMedication();
    }

    private void buildMedication() {
        CustomStatics.REGISTER_MEDICATIONS.put("medication_1", buildMedication("medication_1",30.0));
        CustomStatics.REGISTER_MEDICATIONS.put("medication_2", buildMedication("medication_2",50.0));
        CustomStatics.REGISTER_MEDICATIONS.put("medication_3", buildMedication("medication_3",100.0));
        CustomStatics.REGISTER_MEDICATIONS.put("medication_4", buildMedication("medication_4",150.0));
        CustomStatics.REGISTER_MEDICATIONS.put("medication_5", buildMedication("medication_5",200.0));
        CustomStatics.REGISTER_MEDICATIONS.put("medication_6", buildMedication("medication_6",250.0));
        CustomStatics.REGISTER_MEDICATIONS.put("medication_7", buildMedication("medication_7",300.0));
        CustomStatics.REGISTER_MEDICATIONS.put("medication_8", buildMedication("medication_8",400.0));
        CustomStatics.REGISTER_MEDICATIONS.put("medication_9", buildMedication("medication_9",500.0));
        CustomStatics.REGISTER_MEDICATIONS.put("medication_10", buildMedication("medication_10", 600.0));
    }

    private Medication buildMedication(String code, Double weight) {
        return Medication.builder()
                .code(code)
                .name(RandomStringUtils.randomAlphabetic(10))
                .weight(weight)
                .imageUrl("https://"+code)
                .build();
    }

}
