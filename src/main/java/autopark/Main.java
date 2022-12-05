package autopark;

import autopark.collection.VehicleCollection;
import autopark.infrastructure.core.impl.ApplicationContext;
import autopark.service.Fixer;
import autopark.service.MechanicService;
import autopark.service.ScheduleService;
import autopark.service.Workroom;

import java.util.HashMap;
import java.util.Map;

import static autopark.console.Writer.print;
import static autopark.console.Writer.printError;

public class Main {

    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);
        ApplicationContext applicationContext = new ApplicationContext("autopark", interfaceToImplementation);

        VehicleCollection vehicleCollection = applicationContext.getObject(VehicleCollection.class);
        printAllListInVehicleColl(vehicleCollection);

        Workroom workroom = applicationContext.getObject(Workroom.class);

        ScheduleService scheduleService = applicationContext.getObject(ScheduleService.class);

        scheduleService.checkIsBrokenVehicle(workroom, vehicleCollection);

        try {
            Thread.sleep(666000);
        } catch (InterruptedException e) {
            printError(e.getMessage());
        }
    }


    private static void printAllListInVehicleColl(VehicleCollection vehicleCollection) {
        print(vehicleCollection.getVehiclesList());
        print(vehicleCollection.getRentsList());
        print(vehicleCollection.getTypesList());
    }
}