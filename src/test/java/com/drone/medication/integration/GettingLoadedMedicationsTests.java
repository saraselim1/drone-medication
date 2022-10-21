package com.drone.medication.integration;

import com.drone.medication.DroneApplicationTests;
import com.drone.medication.bootstrap.CustomStatics;
import com.drone.medication.common.enums.ModelEnum;
import com.drone.medication.core.domain.Drone;
import com.drone.medication.core.domain.DroneMedicationDetails;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.BDDAssertions.then;

class GettingLoadedMedicationsTests extends DroneApplicationTests {

    private final static String PATH = "/v1/drones/{serialNumber}/medications";

    @Test
    void getMedications_successfully() {

        String sn = "serial_number_8";
        setup(sn);

        List<DroneMedicationDetails> response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .pathParam("serialNumber", sn)
                .when().get(PATH)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(new TypeRef<List<DroneMedicationDetails>>() {
                });

        then(response).isNotNull();
        then(response).isNotEmpty();
        then(response.size()).isEqualTo(2);

        then(response.get(0).getQuantity()).isEqualTo(3);
        then(response.get(0).getMedication().getCode()).isEqualTo("medication_1");

        then(response.get(1).getQuantity()).isEqualTo(2);
        then(response.get(1).getMedication().getCode()).isEqualTo("medication_3");

    }

    @Test
    void loadMedicationsForNonExistDrone_ThrowBadRequest() {
        String sn = "serial_number_890";

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .pathParam("serialNumber", sn)
                .when().get(PATH)
                .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }

    private void setup(String sn) {

        CustomStatics.REGISTER_DRONES.clear();
        CustomStatics.REGISTER_DRONES.put(sn, Drone.builder()
                .serialNumber(sn)
                .model(ModelEnum.Lightweight)
                .weightLimit(500)
                .build());

        CustomStatics.REGISTER_DRONES.get(sn).setMedicationList(new ArrayList<>());
        CustomStatics.REGISTER_DRONES.get(sn).getMedicationList().add(DroneMedicationDetails.builder()
                        .quantity(3)
                        .medication(CustomStatics.REGISTER_MEDICATIONS.get("medication_1"))
                        .build());

        CustomStatics.REGISTER_DRONES.get(sn).getMedicationList().add(DroneMedicationDetails.builder()
                        .quantity(2)
                        .medication(CustomStatics.REGISTER_MEDICATIONS.get("medication_3"))
                        .build());
    }
}
