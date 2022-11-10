package autopark.entity.engine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractEngine implements Startable {

    private String nameEngineType;
    private double taxCoefficient;

    public AbstractEngine(String nameEngineType, double taxCoefficient) {
        this.nameEngineType = nameEngineType;
        this.taxCoefficient = taxCoefficient;
    }

    @Override
    public String toString() {
        return nameEngineType + ", "
                + "'" + taxCoefficient + "'";
    }
}
