package autopark;

import autopark.collection.VehicleCollection;
import autopark.infrastructure.core.impl.ApplicationContext;
import autopark.service.Fixer;
import autopark.service.MechanicService;
import autopark.service.Workroom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static autopark.console.Writer.print;

public class Main {

    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);
        ApplicationContext applicationContext = new ApplicationContext("autopark", interfaceToImplementation);

        VehicleCollection vehicleCollection = applicationContext.getObject(VehicleCollection.class);
        printAllListInVehicleColl(vehicleCollection);

        Workroom workroom = applicationContext.getObject(Workroom.class);
        workroom.checkAllVehicle(vehicleCollection.getVehiclesList());
    }


    private static void printAllListInVehicleColl(VehicleCollection vehicleCollection) {
        print(vehicleCollection.getVehiclesList());
        print(vehicleCollection.getRentsList());
        print(vehicleCollection.getTypesList());
    }
}