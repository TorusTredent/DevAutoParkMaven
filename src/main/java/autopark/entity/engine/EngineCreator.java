package autopark.entity.engine;

import autopark.entity.Vehicles;

public class EngineCreator {

    public static Startable create(Vehicles vehicles) {
        switch (vehicles.getEngineName()) {
            case "Diesel":
                return new DieselEngine(vehicles.getEngineCapacity(), vehicles.getConsumptionPerKilometer(), vehicles.getTankCapacity());
            case "Electrical":
                return new ElectricalEngine(vehicles.getConsumptionPerKilometer(), vehicles.getTankCapacity());
            case "Gasoline":
                return new GasolineEngine(vehicles.getEngineCapacity(), vehicles.getConsumptionPerKilometer(), vehicles.getTankCapacity());
        }
        return null;
    }
}
