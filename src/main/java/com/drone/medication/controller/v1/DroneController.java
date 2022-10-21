package com.drone.medication.controller.v1;

import com.drone.medication.core.domain.Drone;
import com.drone.medication.core.domain.DroneMedicationDetails;
import com.drone.medication.core.payload.request.AddingDroneRequest;
import com.drone.medication.core.payload.request.LoadMedicationRequest;
import com.drone.medication.core.payload.response.DroneBatteryResponse;
import com.drone.medication.core.service.DroneService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/drones")
@AllArgsConstructor
public class DroneController {

    private final DroneService droneService;

    @PostMapping( produces = "application/json")
    public ResponseEntity<Drone> addDrone(@RequestBody @Valid AddingDroneRequest addingDroneRequest) {
        return new ResponseEntity<>(droneService.addNewDrone(addingDroneRequest), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{serialNumber}", produces = "application/json")
    public ResponseEntity<Void> loadDroneWithMedications(@PathVariable String serialNumber,
                                                     @RequestBody @Valid LoadMedicationRequest loadMedicationRequest) {
        droneService.loadDroneWithMedications(serialNumber,loadMedicationRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{serialNumber}/medications", produces = "application/json")
    public ResponseEntity<List<DroneMedicationDetails>> getLoadedMedications(@PathVariable String serialNumber) {
        return new ResponseEntity<>(droneService.getLoadedMedications(serialNumber),HttpStatus.OK);
    }

    @GetMapping(value = "/available", produces = "application/json")
    public ResponseEntity<List<Drone>> getAvailableDrones() {
        return new ResponseEntity<>(droneService.getAvailableDrones(),HttpStatus.OK);
    }

    @GetMapping(value = "/{serialNumber}/battery", produces = "application/json")
    public ResponseEntity<DroneBatteryResponse> getDroneBattery(@PathVariable String serialNumber) {
        return new ResponseEntity<>(droneService.getDroneBattery(serialNumber),HttpStatus.OK);
    }


}
