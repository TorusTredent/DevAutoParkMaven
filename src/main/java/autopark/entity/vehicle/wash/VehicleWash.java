package autopark.entity.vehicle.wash;

import autopark.collection.MyArrayQueue;
import autopark.entity.vehicle.Vehicle;
import autopark.console.Writer;

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
