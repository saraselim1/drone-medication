package com.drone.medication.core.listener;

import com.drone.medication.core.event.BatteryAuditEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class BatteryEventListener {

    @EventListener
    @Async
    public void onBatteryEventListener(BatteryAuditEvent event) {
        log.info("Drone of serial number {}, its battery percentage is {}",
                event.getSerialNumber(), event.getBatteryPercent());
    }
}
