package com.drone.medication.integration;

import com.drone.medication.DroneApplicationTests;
import com.drone.medication.bootstrap.CustomStatics;
import com.drone.medication.common.enums.ModelEnum;
import com.drone.medication.common.enums.StateEnum;
import com.drone.medication.core.domain.Drone;
import com.drone.medication.core.payload.request.AddingDroneRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.BDDAssertions.then;

class AddingNewDroneTests extends DroneApplicationTests {

    private final static String PATH = "/v1/drones";

    @Test
    void addDrone_successfully() {

        String sn = "serial_number_1";
        AddingDroneRequest request = AddingDroneRequest.builder()
                .serialNumber(sn)
                .model(ModelEnum.Lightweight)
                .weightLimit(300)
                .build();

        Drone response =
                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(request)
                .when().post(PATH)
                .then().assertThat().statusCode(HttpStatus.CREATED.value())
                .extract().as(Drone.class);

        then(response).isNotNull();
        then(response.getBatteryCapacity()).isNotNull();
        then(response.getState()).isNotNull();
        then(response.getState()).isEqualTo(StateEnum.IDLE);

        then(CustomStatics.REGISTER_DRONES.containsKey(sn)).isTrue();
        then(CustomStatics.REGISTER_DRONES.get(sn)).isNotNull();
        then(CustomStatics.REGISTER_DRONES.get(sn).getModel()).isNotNull();
        then(CustomStatics.REGISTER_DRONES.get(sn).getModel()).isEqualTo(ModelEnum.Lightweight);
        then(CustomStatics.REGISTER_DRONES.get(sn).getWeightLimit()).isNotNull();
        then(CustomStatics.REGISTER_DRONES.get(sn).getWeightLimit()).isEqualTo(300);
    }

    @Test
    void addDuplicateSerialNumber_throwError() {

        String sn = "serial_1234";
        AddingDroneRequest request = AddingDroneRequest.builder()
                .serialNumber(sn)
                .model(ModelEnum.Lightweight)
                .weightLimit(300)
                .build();

        AddingDroneRequest duplicateSerialNumberRequest = AddingDroneRequest.builder()
                .serialNumber(sn)
                .model(ModelEnum.Heavyweight)
                .weightLimit(400)
                .build();

        Drone response =
                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(request)
                        .when().post(PATH)
                        .then().assertThat().statusCode(HttpStatus.CREATED.value())
                        .extract().as(Drone.class);

                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(duplicateSerialNumberRequest)
                        .when().post(PATH)
                        .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());

        then(response).isNotNull();
        then(response.getBatteryCapacity()).isNotNull();
        then(response.getState()).isNotNull();
        then(response.getState()).isEqualTo(StateEnum.IDLE);

        then(CustomStatics.REGISTER_DRONES.containsKey(sn)).isTrue();
        then(CustomStatics.REGISTER_DRONES.get(sn)).isNotNull();
        then(CustomStatics.REGISTER_DRONES.get(sn).getModel()).isNotNull();
        then(CustomStatics.REGISTER_DRONES.get(sn).getModel()).isEqualTo(ModelEnum.Lightweight);
        then(CustomStatics.REGISTER_DRONES.get(sn).getWeightLimit()).isNotNull();
        then(CustomStatics.REGISTER_DRONES.get(sn).getWeightLimit()).isEqualTo(300);
    }

    @Test
    void addDroneWithMissingSerialNumber_throwError() {

        AddingDroneRequest request = AddingDroneRequest.builder()
                .model(ModelEnum.Lightweight)
                .weightLimit(300)
                .build();

                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(request)
                        .when().post(PATH)
                        .then().assertThat().statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void addDroneExceedMaxWL_throwError() {

        String sn = "serial_number_2";
        AddingDroneRequest request = AddingDroneRequest.builder()
                .serialNumber(sn)
                .model(ModelEnum.Lightweight)
                .weightLimit(600)
                .build();

        given().contentType(ContentType.JSON).accept(ContentType.JSON).body(request)
                .when().post(PATH)
                .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());

    }

}
