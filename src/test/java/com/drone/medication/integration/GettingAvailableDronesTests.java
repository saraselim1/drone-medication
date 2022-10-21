package com.drone.medication.integration;

import com.drone.medication.DroneApplicationTests;
import com.drone.medication.bootstrap.CustomStatics;
import com.drone.medication.common.enums.ModelEnum;
import com.drone.medication.common.enums.StateEnum;
import com.drone.medication.core.domain.Drone;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.BDDAssertions.then;

public class GettingAvailableDronesTests extends DroneApplicationTests {

    private final static String PATH = "/v1/drones/available";
    private final static List<String> availableSerialNumber = Arrays.asList("sn_1", "sn_2");

    @Test
    void getAvailableDrones_successfully() {
        setup();
        List<Drone> response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().get(PATH)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract().as(new TypeRef<List<Drone>>() {
                });

        then(response).isNotNull();
        then(response).isNotEmpty();

        then(response.size()).isEqualTo(2);

        then(response.get(0).getSerialNumber()).isEqualTo(availableSerialNumber.get(0));
        then(response.get(1).getSerialNumber()).isEqualTo(availableSerialNumber.get(1));
    }

    private void setup() {

        CustomStatics.REGISTER_DRONES.clear();

        addAvailableDrone();
        addNonAvailableDrone("loading_sn", StateEnum.LOADING);
        addNonAvailableDrone("loaded_sn", StateEnum.LOADED);
        addNonAvailableDrone("delivering_sn", StateEnum.DELIVERING);
        addNonAvailableDrone("delivered_sn", StateEnum.DELIVERED);
        addNonAvailableDrone("returning_sn", StateEnum.RETURNING);
        addLowBatteryDrone();
    }

    private void addLowBatteryDrone() {
        String sn = "low_battery_sn";
        CustomStatics.REGISTER_DRONES.put(sn, Drone.builder()
                .serialNumber(sn)
                .model(ModelEnum.Lightweight)
                .weightLimit(500)
                .batteryCapacity(10)
                .state(StateEnum.IDLE)
                .build());
    }

    private void addNonAvailableDrone(String sn, StateEnum state) {
        CustomStatics.REGISTER_DRONES.put(sn, Drone.builder()
                .serialNumber(sn)
                .model(ModelEnum.Lightweight)
                .weightLimit(500)
                .batteryCapacity(60)
                .state(state)
                .build());
    }

    private void addAvailableDrone() {
        CustomStatics.REGISTER_DRONES.put(availableSerialNumber.get(0), Drone.builder()
                .serialNumber(availableSerialNumber.get(0))
                .model(ModelEnum.Lightweight)
                .weightLimit(500)
                .batteryCapacity(60)
                .state(StateEnum.IDLE)
                .build());

        CustomStatics.REGISTER_DRONES.put(availableSerialNumber.get(1), Drone.builder()
                .serialNumber(availableSerialNumber.get(1))
                .model(ModelEnum.Lightweight)
                .weightLimit(500)
                .batteryCapacity(60)
                .state(StateEnum.IDLE)
                .build());
    }
}
