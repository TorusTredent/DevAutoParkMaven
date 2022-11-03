package by.incubator.sorter;

import by.incubator.entity.vehicle.Vehicle;

public class Sorter {
    
    public void sortingVehicles(Vehicle[] vehicles) {
        for (int i = 0; i < vehicles.length; i++) {
            int minVehicleIndex = i;
            for (int j = i; j < vehicles.length; j++) {
                if (vehicles[j].compareTo(vehicles[minVehicleIndex]) < 0) {
                    minVehicleIndex = j;
                }
            }
            swap(vehicles, i, minVehicleIndex);
        }
    }


    private void swap(Vehicle[] vehicles, int i, int minVehicleIndex) {
        Vehicle temp = vehicles[i];
        vehicles[i] = vehicles[minVehicleIndex];
        vehicles[minVehicleIndex] = temp;
    }
}
