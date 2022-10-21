package com.drone.medication.core.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BatteryAuditEvent extends ApplicationEvent {

    private String serialNumber;
    private Integer batteryPercent;

    public BatteryAuditEvent(Object source, String serialNumber, Integer batteryPercent) {
        super(source);
        this.serialNumber = serialNumber;
        this.batteryPercent = batteryPercent;
    }
}
