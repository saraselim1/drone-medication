package com.drone.medication.integration;

import com.drone.medication.DroneApplicationTests;
import com.drone.medication.bootstrap.CustomStatics;
import com.drone.medication.common.enums.ModelEnum;
import com.drone.medication.core.domain.Drone;
import com.drone.medication.core.payload.response.DroneBatteryResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.BDDAssertions.then;

public class GettingDroneBatteryTests extends DroneApplicationTests {

    private final static String PATH = "/v1/drones/{serialNumber}/battery";

    @Test
    void getDroneBattery_successfully() {

        String sn = "serial_number_9";
        setup(sn);

        DroneBatteryResponse response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .pathParam("serialNumber", sn)
                .when().get(PATH)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(DroneBatteryResponse.class);

        then(response).isNotNull();
        then(response.getSerialNumber()).isEqualTo(sn);
        Integer batteryCapacity = CustomStatics.REGISTER_DRONES.get(sn).getBatteryCapacity();
        then(response.getBatteryPercentage()).isEqualTo(batteryCapacity);

    }

    @Test
    void getBatteryForNonExistDrone_badRequest() {

        String sn = "serial_number_NotExist";

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .pathParam("serialNumber", sn)
                .when().get(PATH)
                .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private void setup(String sn) {

        CustomStatics.REGISTER_DRONES.clear();
        CustomStatics.REGISTER_DRONES.put(sn, Drone.builder()
                .serialNumber(sn)
                .model(ModelEnum.Lightweight)
                .weightLimit(500)
                .batteryCapacity(60)
                .build());
    }
}
