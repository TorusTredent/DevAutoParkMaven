package autopark.service;

import autopark.collection.VehicleCollection;
import autopark.infrastructure.threads.annotations.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ScheduleService {

    private List<Long> repairedVehiclesIds = new ArrayList<>();
    private LocalTime updateTime;
    private LocalTime currentTime;

    @Schedule(timeout = 30000, delta = 30000)
    public void checkIsBrokenVehicle(Workroom workroom, VehicleCollection vehicleCollection) {
        updateTime();
        repairedVehiclesIds = workroom.getRepairedVehiclesIds(vehicleCollection.getVehiclesList());
    }

    private void updateTime() {
        currentTime = LocalTime.now();
        if (currentTime.getHour() == updateTime.getHour()) {
            if (currentTime.getMinute() - updateTime.getMinute() >= 5) {
                updateTime = currentTime;
            }
        }
    }
}
