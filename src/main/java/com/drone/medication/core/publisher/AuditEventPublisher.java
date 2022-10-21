package com.drone.medication.core.publisher;

import com.drone.medication.core.event.BatteryAuditEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuditEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishAuditEvent(final String serialNumber, final Integer batteryPercent) {
        BatteryAuditEvent event = new BatteryAuditEvent(this, serialNumber, batteryPercent);
        applicationEventPublisher.publishEvent(event);
    }
}
