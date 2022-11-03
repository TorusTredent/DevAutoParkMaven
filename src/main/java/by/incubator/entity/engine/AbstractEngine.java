package by.incubator.entity.engine;

public abstract class AbstractEngine implements Startable {

    private String nameEngineType;
    private double taxCoefficient;

    public AbstractEngine(String nameEngineType, double taxCoefficient) {
        this.nameEngineType = nameEngineType;
        this.taxCoefficient = taxCoefficient;
    }

    public String getNameEngineType() {
        return nameEngineType;
    }

    public void setNameEngineType(String nameEngineType) {
        this.nameEngineType = nameEngineType;
    }

    public double getTaxCoefficient() {
        return taxCoefficient;
    }

    public void setTaxCoefficient(double taxCoefficient) {
        this.taxCoefficient = taxCoefficient;
    }

    @Override
    public String toString() {
        return nameEngineType + ", "
                + "'" + taxCoefficient + "'";
    }
}
