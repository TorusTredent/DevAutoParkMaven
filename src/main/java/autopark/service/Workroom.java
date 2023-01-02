package autopark.service;

import autopark.console.Writer;
import autopark.entity.Vehicles;
import autopark.infrastructure.core.annotations.Autowired;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class Workroom {

    @Autowired
    private Fixer fixer;


    public void checkAllVehicle(List<Vehicles> vehicles) {
        List<Vehicles> notBrokenVehicles = new ArrayList<>();
        for (Vehicles vehicle : vehicles) {
            if (!fixer.isBroken(vehicle)) {
                notBrokenVehicles.add(vehicle);
            } else {
                Writer.print("Auto " + vehicle.getId() + " faulty, fix it.");
                fixer.repair(vehicle);
            }
        }
        Writer.print("Everything is in excellent condition");
        for (Vehicles vehicle : notBrokenVehicles) {
            Writer.print(vehicle.getModelName());
        }
    }

    public List<Vehicles> getRepairedVehicles(List<Vehicles> vehicles) {
        List<Vehicles> repVehicles = new ArrayList<>();

        vehicles.forEach(vehicle -> {
            fixer.detectBreaking(vehicle);
            if (fixer.isBroken(vehicle)) {
                repVehicles.add(vehicle);
                fixer.repair(vehicle);
            }
        });
        return repVehicles;
    }


    public List<Long> getRepairedVehiclesIds(List<Vehicles> vehicles) {
        List<Vehicles> repVEhicles = getRepairedVehicles(vehicles);
        return repVEhicles.stream()
                .map(Vehicles::getId)
                .collect(Collectors.toList());
    }
}
