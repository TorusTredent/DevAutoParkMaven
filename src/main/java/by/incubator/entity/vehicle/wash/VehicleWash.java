package by.incubator.entity.vehicle.wash;

import by.incubator.collection.MyArrayQueue;
import by.incubator.console.Writer;
import by.incubator.entity.vehicle.Vehicle;

public class VehicleWash {

    private static MyArrayQueue<Vehicle> queue = new MyArrayQueue<>();

    public void checkIn(Vehicle vehicle) {
        queue.enqueue(vehicle);
    }

    public void wash() {
        Vehicle vehicle = queue.dequeue();
        Writer.print("\n" + vehicle.getModelName() + " вымыто");
    }
}
