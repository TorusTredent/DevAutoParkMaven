package autopark;

import autopark.collection.VehicleCollection;
import autopark.infrastructure.core.impl.ApplicationContext;
import autopark.infrastructure.dto.PostgresDataBase;
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

        PostgresDataBase postgresDataBase = applicationContext.getObject(PostgresDataBase.class);
    }
}