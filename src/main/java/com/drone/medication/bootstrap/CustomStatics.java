package com.drone.medication.bootstrap;

import com.drone.medication.core.domain.Drone;
import com.drone.medication.core.domain.Medication;

import java.util.HashMap;
import java.util.Map;

public final class CustomStatics {

    private CustomStatics() {
        throw new IllegalStateException("Utility class");
    }

    public static final Map<String, Drone> REGISTER_DRONES = new HashMap<>();
    public static final Map<String, Medication> REGISTER_MEDICATIONS= new HashMap<>();

}
