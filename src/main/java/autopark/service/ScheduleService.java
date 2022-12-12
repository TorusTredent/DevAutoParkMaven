package autopark.service;

import autopark.collection.VehicleCollection;
import autopark.infrastructure.threads.annotations.Schedule;

public class ScheduleService {

    @Schedule(timeout = 10000, delta = 10000)
    public void checkIsBrokenVehicle(Workroom workroom, VehicleCollection vehicleCollection) {
        workroom.checkAllVehicle(vehicleCollection.getVehiclesList());
    }
}