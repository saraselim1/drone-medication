package com.drone.medication.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Medication {

    @Pattern(message = "Invalid pattern.", regexp = "^[a-zA-Z\\d_-]*$")
    @NotNull
    private String name;

    @NotNull
    private Double weight;

    @Pattern(message="Invalid pattern.", regexp = "^\\w*$")
    @NotNull
    private String code;
    private String imageUrl;
}
