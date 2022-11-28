package autopark.entity.engine;

public class GasolineEngine extends CombustionEngine {

    private static final String NAME = "Gasoline";
    public GasolineEngine(double engineCapacity, double fuelConsumptionPer100, double fuelTankCapacity) {
        super(NAME, 1.1, engineCapacity, fuelConsumptionPer100, fuelTankCapacity);
    }
}
