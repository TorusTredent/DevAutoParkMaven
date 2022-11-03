package by.incubator.entity;

import by.incubator.entity.engine.DieselEngine;
import by.incubator.entity.engine.ElectricEngine;
import by.incubator.entity.engine.GasolineEngine;
import by.incubator.entity.enums.Color;
import by.incubator.entity.vehicle.VehicleType;

public class TechnicalSpecialist {

    public static final int LOWER_LIMIT_MANUFACTURE_YEAR = 1886;

    public static boolean validateManufactureYear(int year) {
        return (year >= LOWER_LIMIT_MANUFACTURE_YEAR && year < 10000);
    }

    public static boolean validateMileage(int mileage) {
        return mileage >= 0;
    }

    public static boolean validateWeight(int weight) {
        return weight >= 0;
    }

    public static boolean validateColor(Color color) {
        return color != null;
    }

    public static boolean validateVehicleType(VehicleType type) {
        return (type!= null && !type.getName().isEmpty() &&
                type.getName() != null && type.getRoadTaxCoefficient() >= 0);
    }

    public static boolean validateRegistrationNumber(String number) {
        return (number != null && number.matches("\\d{4}\\s[A-Z]{2}(-)\\d"));
    }

    public static boolean validateModelName(String name) {
        return !name.isEmpty();
    }

    public static boolean validationGasolineEngine(GasolineEngine gasolineEngine) {
        return (gasolineEngine != null && gasolineEngine.getEngineCapacity() >= 0 &&
                gasolineEngine.getFuelConsumptionPer100() >= 0 && gasolineEngine.getFuelTankCapacity() >= 0);
    }

    public static boolean validationDieselEngine(DieselEngine dieselEngine) {
        return (dieselEngine != null && dieselEngine.getEngineCapacity() >= 0 &&
                dieselEngine.getFuelConsumptionPer100() >= 0 && dieselEngine.getFuelTankCapacity() >= 0);
    }

    public static boolean validationElectricalEngine(ElectricEngine electricEngine) {
        return (electricEngine.getBatterySize() >= 0 && electricEngine.getElectricityConsumption() >= 0);
    }
}
