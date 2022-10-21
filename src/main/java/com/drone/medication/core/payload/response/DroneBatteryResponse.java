package com.drone.medication.core.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneBatteryResponse {

    @NotNull
    private String serialNumber;

    @NotNull
    private Integer batteryPercentage;
}
