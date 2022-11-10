package autopark.service;

import autopark.entity.Types;
import autopark.entity.engine.DieselEngine;
import autopark.entity.engine.ElectricalEngine;
import autopark.entity.engine.GasolineEngine;
import autopark.entity.vehicle.VehicleType;

public class TechnicalSpecialist {

    public static final int LOWER_LIMIT_MANUFACTURE_YEAR = 1886;

    public TechnicalSpecialist() {
    }

    public static boolean validateManufactureYear(int year) {
        return (year >= LOWER_LIMIT_MANUFACTURE_YEAR && year < 10000);
    }

    public static boolean validateMileage(int mileage) {
        return mileage >= 0;
    }

    public static boolean validateWeight(double weight) {
        return weight >= 0;
    }

    public static boolean validateColor(String color) {
        return color != null;
    }

    public static boolean validateVehicleType(Types type) {
        return (type!= null && !type.getName().isEmpty() &&
                type.getName() != null && type.getCoefTaxes() >= 0);
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

    public static boolean validationElectricalEngine(ElectricalEngine electricalEngine) {
        return (electricalEngine.getBatterySize() >= 0 && electricalEngine.getElectricityConsumption() >= 0);
    }

    public static boolean validateCoefTaxes(Double coefTaxes) {
        return coefTaxes >= 0;
    }

    public static boolean validateTypeName(String name) {
        return name != null && !name.isEmpty();
    }

    public static boolean validateCost(Double cost) {
        return cost >= 0;
    }

    public static boolean validateEngineName(String engineName) {
        return "Diesel".equals(engineName) || "Electrical".equals(engineName) || "Gasoline".equals(engineName);
    }
}
