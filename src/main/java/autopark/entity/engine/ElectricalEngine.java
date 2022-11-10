package autopark.entity.engine;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ElectricalEngine extends AbstractEngine{

    private double batterySize;
    private double electricityConsumption;

    private static final String NAME = "Electrical";

    public ElectricalEngine(double batterySize, double electricityConsumption) {
        super(NAME, 0.1);
        this.batterySize = batterySize;
        this.electricityConsumption = electricityConsumption;
    }

    @Override
    public double getTaxPerMonth() {
        return 0;
    }

    @Override
    public double getMaxKilometers() {
        return batterySize/electricityConsumption;
    }
}
