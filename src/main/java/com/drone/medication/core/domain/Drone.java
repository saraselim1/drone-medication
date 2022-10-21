package com.drone.medication.core.domain;

import com.drone.medication.common.enums.ModelEnum;
import com.drone.medication.common.enums.StateEnum;
import com.drone.medication.core.payload.request.MedicationDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Drone {

    @Size(min = 1, max = 100)
    private String serialNumber;

    @NotNull
    private ModelEnum model;

    @NotNull
    @Max(500)
    private double weightLimit;

    @NotNull
    private Integer batteryCapacity;

    @NotNull
    private StateEnum state;

    private List<DroneMedicationDetails> medicationList;
}
