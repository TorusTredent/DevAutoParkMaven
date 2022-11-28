package autopark.sorter;

import autopark.entity.Vehicles;

public class Sorter {
    
    public void sortingVehicles(Vehicles[] vehicles) {
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


    private void swap(Vehicles[] vehicles, int i, int minVehicleIndex) {
        Vehicles temp = vehicles[i];
        vehicles[i] = vehicles[minVehicleIndex];
        vehicles[minVehicleIndex] = temp;
    }
}
