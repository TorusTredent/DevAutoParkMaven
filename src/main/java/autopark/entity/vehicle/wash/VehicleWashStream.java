package autopark.entity.vehicle.wash;

import autopark.collection.MyArrayQueue;
import autopark.entity.vehicle.Vehicle;
import autopark.console.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class VehicleWashStream {

    private static MyArrayQueue<Vehicle> queue = new MyArrayQueue<>();

    public static void checkIn(List<Vehicle> vehicles) {
        vehicles.forEach(vehicle -> queue.enqueue(vehicle));
    }

    public static void wash() {
        int size = queue.size();
        List<Vehicle> vehicles = new ArrayList<>();
        IntStream.range(0, size).mapToObj(i -> queue.dequeue()).forEach(vehicle -> {
            vehicles.add(vehicle);
            Writer.print("\n" + vehicle.getModelName() + " вымыто");
        });
    }
}
