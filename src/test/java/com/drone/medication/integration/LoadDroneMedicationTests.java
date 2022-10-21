package com.drone.medication.integration;

import com.drone.medication.DroneApplicationTests;
import com.drone.medication.bootstrap.CustomStatics;
import com.drone.medication.common.enums.ModelEnum;
import com.drone.medication.common.enums.StateEnum;
import com.drone.medication.core.domain.Drone;
import com.drone.medication.core.payload.request.LoadMedicationRequest;
import com.drone.medication.core.payload.request.MedicationDetails;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.BDDAssertions.then;

class LoadDroneMedicationTests extends DroneApplicationTests {

    private final static String PATH = "/v1/drones/{serialNumber}";

    @Test
    void loadMedications_successfully() {

        String sn = "serial_number_3";
        setup(sn);

        List<MedicationDetails> medicationDetails = new ArrayList<>();
        medicationDetails.add(
                MedicationDetails.builder()
                        .code("medication_1")
                        .quantity(3)
                        .build());
        medicationDetails.add(
                MedicationDetails.builder()
                        .code("medication_3")
                        .quantity(2)
                        .build());
        LoadMedicationRequest request = LoadMedicationRequest.builder()
                .medications(medicationDetails)
                .build();

        given().contentType(ContentType.JSON).accept(ContentType.JSON).body(request)
                .pathParam("serialNumber", sn)
                .when().post(PATH)
                .then().assertThat().statusCode(HttpStatus.OK.value());

        then(CustomStatics.REGISTER_DRONES.containsKey(sn)).isTrue();
        then(CustomStatics.REGISTER_DRONES.get(sn).getState()).isEqualTo(StateEnum.LOADED);
        then(CustomStatics.REGISTER_DRONES.get(sn).getMedicationList()).isNotNull();
        then(CustomStatics.REGISTER_DRONES.get(sn).getMedicationList()).isNotEmpty();
        then(CustomStatics.REGISTER_DRONES.get(sn).getMedicationList().size()).isEqualTo(2);

        then(CustomStatics.REGISTER_DRONES.get(sn).getMedicationList().get(0).getQuantity()).isEqualTo(3);
        then(CustomStatics.REGISTER_DRONES.get(sn).getMedicationList().get(0).getMedication().getCode()).isEqualTo("medication_1");

        then(CustomStatics.REGISTER_DRONES.get(sn).getMedicationList().get(1).getQuantity()).isEqualTo(2);
        then(CustomStatics.REGISTER_DRONES.get(sn).getMedicationList().get(1).getMedication().getCode()).isEqualTo("medication_3");

    }

    @Test
    void loadMedicationsForNonExistDrone_ThrowBadRequest() {
        String sn = "serial_number_890";
        List<MedicationDetails> medicationDetails = new ArrayList<>();
        medicationDetails.add(
                MedicationDetails.builder()
                        .code("medication_1")
                        .quantity(3)
                        .build());
        medicationDetails.add(
                MedicationDetails.builder()
                        .code("medication_3")
                        .quantity(2)
                        .build());
        LoadMedicationRequest request = LoadMedicationRequest.builder()
                .medications(medicationDetails)
                .build();

        given().contentType(ContentType.JSON).accept(ContentType.JSON).body(request)
                .pathParam("serialNumber", sn)
                .when().post(PATH)
                .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }

    @Test
    void loadMedicationsToLowBattery_ThrowBadRequest() {

        String sn = "serial_number_5";
        setup(sn);
        CustomStatics.REGISTER_DRONES.get(sn).setBatteryCapacity(20);

        List<MedicationDetails> medicationDetails = new ArrayList<>();
        medicationDetails.add(
                MedicationDetails.builder()
                        .code("medication_1")
                        .quantity(3)
                        .build());
        medicationDetails.add(
                MedicationDetails.builder()
                        .code("medication_3")
                        .quantity(2)
                        .build());
        LoadMedicationRequest request = LoadMedicationRequest.builder()
                .medications(medicationDetails)
                .build();

        given().contentType(ContentType.JSON).accept(ContentType.JSON).body(request)
                .pathParam("serialNumber", sn)
                .when().post(PATH)
                .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());

        then(CustomStatics.REGISTER_DRONES.containsKey(sn)).isTrue();
        then(CustomStatics.REGISTER_DRONES.get(sn).getMedicationList()).isNull();
    }

    @Test
    void loadNonExistMedications_ThrowBadRequest() {

        String sn = "serial_number_6";
        setup(sn);
        CustomStatics.REGISTER_DRONES.get(sn).setBatteryCapacity(20);

        List<MedicationDetails> medicationDetails = new ArrayList<>();
        medicationDetails.add(
                MedicationDetails.builder()
                        .code("medication_00")
                        .quantity(3)
                        .build());
        medicationDetails.add(
                MedicationDetails.builder()
                        .code("medication_3")
                        .quantity(2)
                        .build());
        LoadMedicationRequest request = LoadMedicationRequest.builder()
                .medications(medicationDetails)
                .build();

        given().contentType(ContentType.JSON).accept(ContentType.JSON).body(request)
                .pathParam("serialNumber", sn)
                .when().post(PATH)
                .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());

        then(CustomStatics.REGISTER_DRONES.containsKey(sn)).isTrue();
        then(CustomStatics.REGISTER_DRONES.get(sn).getMedicationList()).isNull();
    }

    @Test
    void loadOverWeightMedications_ThrowBadRequest() {

        String sn = "serial_number_7";
        setup(sn);

        List<MedicationDetails> medicationDetails = new ArrayList<>();
        medicationDetails.add(
                MedicationDetails.builder()
                        .code("medication_6")
                        .quantity(2)
                        .build());
        medicationDetails.add(
                MedicationDetails.builder()
                        .code("medication_3")
                        .quantity(2)
                        .build());
        LoadMedicationRequest request = LoadMedicationRequest.builder()
                .medications(medicationDetails)
                .build();

        given().contentType(ContentType.JSON).accept(ContentType.JSON).body(request)
                .pathParam("serialNumber", sn)
                .when().post(PATH)
                .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();

        then(CustomStatics.REGISTER_DRONES.containsKey(sn)).isTrue();
        then(CustomStatics.REGISTER_DRONES.get(sn).getMedicationList()).isNull();
    }

    private void setup(String sn) {
        CustomStatics.REGISTER_DRONES.clear();
        CustomStatics.REGISTER_DRONES.put(sn, Drone.builder()
                .serialNumber(sn)
                .model(ModelEnum.Lightweight)
                .weightLimit(500)
                        .batteryCapacity(90)
                .build());
    }
}
