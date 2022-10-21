package com.drone.medication.core.service;

import com.drone.medication.bootstrap.CustomStatics;
import com.drone.medication.core.domain.Drone;
import com.drone.medication.core.publisher.AuditEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
@AllArgsConstructor
public class BatteryService {

    private final AuditEventPublisher publisher;

    @PostConstruct
    private void methodToExecute() {
        checkBattery();
    }

    @Async
    @Scheduled(fixedRate = 1000)
    public void checkBattery(){
        for (Map.Entry<String, Drone> entry : CustomStatics.REGISTER_DRONES.entrySet()) {
            publisher.publishAuditEvent(entry.getValue().getSerialNumber(),
                    entry.getValue().getBatteryCapacity());
        }
    }
}
