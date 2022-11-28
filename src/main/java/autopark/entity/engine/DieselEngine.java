package autopark.entity.engine;

public class DieselEngine extends CombustionEngine{

    private static final String NAME = "Diesel";

    public DieselEngine(double engineCapacity, double fuelConsumptionPer100, double fuelTankCapacity) {
        super(NAME, 1.2, engineCapacity, fuelConsumptionPer100, fuelTankCapacity);
    }
}
