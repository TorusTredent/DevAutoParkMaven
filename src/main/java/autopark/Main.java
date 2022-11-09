package autopark;

import autopark.infrastructure.core.impl.ApplicationContext;
import autopark.service.BadMechanicService;
import autopark.service.Workroom;
import autopark.collection.VehicleCollection;
import autopark.entity.Fixer;
import autopark.entity.vehicle.Vehicle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, BadMechanicService.class);
        ApplicationContext applicationContext = new ApplicationContext("autopark", interfaceToImplementation);

        VehicleCollection vehicleCollection = applicationContext.getObject(VehicleCollection.class);
        Workroom workroom = applicationContext.getObject(Workroom.class);

        List<Vehicle> vehicleList = vehicleCollection.getVehicleList();
        workroom.checkAllVehicle(vehicleList);
    }
}