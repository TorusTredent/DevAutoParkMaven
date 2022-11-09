package autopark.service;

import autopark.console.Writer;
import autopark.entity.Fixer;
import autopark.entity.vehicle.Vehicle;
import autopark.infrastructure.core.annotations.Autowired;

import java.util.ArrayList;
import java.util.List;

public class Workroom {

    @Autowired
    private Fixer fixer;

    public Workroom() {
    }

    public Fixer getFixer() {
        return fixer;
    }

    public void setFixer(Fixer fixer) {
        this.fixer = fixer;
    }

    public void checkAllVehicle(List<Vehicle> vehicles) {
        List<Vehicle> notBrokenVehicle = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (!fixer.isBroken(vehicle)) {
                notBrokenVehicle.add(vehicle);
            } else {
                Writer.print("Auto " + vehicle.getId() + " faulty, fix it.");
                fixer.repair(vehicle);
            }
        }
        Writer.print("Everything is in excellent condition");
        for (Vehicle vehicle : notBrokenVehicle) {
            Writer.print(vehicle.getModelName());
        }
    }
}
