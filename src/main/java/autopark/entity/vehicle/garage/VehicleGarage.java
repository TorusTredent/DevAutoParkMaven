package autopark.entity.vehicle.garage;

import autopark.collection.MyArrayStack;
import autopark.console.Writer;
import autopark.entity.vehicle.Vehicle;

public class VehicleGarage {

    private static MyArrayStack<Vehicle> stack = new MyArrayStack<>();

    public void checkIn(Vehicle vehicle) {
        stack.push(vehicle);
        Writer.print(vehicle.getModelName() + " заехало в гараж");
    }

    public void leave() {
        Vehicle vehicle;
        try {
            vehicle = stack.pop();
            Writer.print(vehicle.getModelName() + " выехало из гаража");
        } catch (IllegalArgumentException e) {
            Writer.printError(e.getMessage());
        }
    }
}
