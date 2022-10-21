package com.drone.medication.util;

import com.drone.medication.common.enums.StateEnum;
import com.drone.medication.core.domain.Drone;
import com.drone.medication.core.payload.request.AddingDroneRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DroneUtil {

    public static Drone buildDrone(AddingDroneRequest addingDroneRequest) {
        return Drone.builder()
                .serialNumber(addingDroneRequest.getSerialNumber())
                .model(addingDroneRequest.getModel())
                .weightLimit(addingDroneRequest.getWeightLimit())
                .batteryCapacity((int) RandomUtil.getRandomNumber(0,100))
                .state(StateEnum.IDLE)
                .build();
    }
}
