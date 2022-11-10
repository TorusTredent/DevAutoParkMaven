package autopark.comparator;

import autopark.entity.Vehicles;

import java.util.Comparator;

public class VehicleComparator implements Comparator<Vehicles> {

    @Override
    public int compare(Vehicles o1, Vehicles o2) {
        return o1.getModelName().compareTo(o2.getModelName());
    }
}
