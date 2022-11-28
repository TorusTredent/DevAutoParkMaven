package autopark.entity.engine;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CombustionEngine extends AbstractEngine{

    private double engineCapacity;
    private double fuelConsumptionPer100;
    private double fuelTankCapacity;

    public CombustionEngine(String nameEngineType, double taxCoefficient, double engineCapacity, double fuelConsumptionPer100, double fuelTankCapacity) {
        super(nameEngineType, taxCoefficient);
        this.engineCapacity = engineCapacity;
        this.fuelConsumptionPer100 = fuelConsumptionPer100;
        this.fuelTankCapacity = fuelTankCapacity;
    }

    @Override
    public double getTaxPerMonth() {
        return 0;
    }

    @Override
    public double getMaxKilometers() {
        return fuelTankCapacity * 100 / fuelConsumptionPer100;
     }
}
