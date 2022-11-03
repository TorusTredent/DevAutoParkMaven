package by.incubator.entity.vehicle.garage;

import by.incubator.collection.MyArrayStack;
import by.incubator.console.Writer;
import by.incubator.entity.vehicle.Vehicle;

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
