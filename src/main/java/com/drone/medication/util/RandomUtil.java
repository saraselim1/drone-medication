package com.drone.medication.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomUtil {

    public static double getRandomNumber(int min, int max) {
        return  ((Math.random() * (max - min)) + min);
    }
}
