package com.drone.medication.core.payload.request;

import com.drone.medication.common.enums.ModelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddingDroneRequest {

    @Size(min = 1, max = 100)
    private String serialNumber;

    @NotNull
    private ModelEnum model;

    @NotNull
    @Max(500)
    private double weightLimit;

}
