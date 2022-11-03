package by.incubator.entity.vehicle;

public class VehicleType {

    private int id;
    private String name;
    private double roadTaxCoefficient;

    public VehicleType() {
    }

    public VehicleType(String name, double roadTaxCoefficient) {
        this.name = name;
        this.roadTaxCoefficient = roadTaxCoefficient;
    }

    public VehicleType(int id, String name, double roadTaxCoefficient) {
        this.id = id;
        this.name = name;
        this.roadTaxCoefficient = roadTaxCoefficient;
    }

    public void display() {
        System.out.println( "name = " + name + "\n" + "roadTaxCoefficient = " + roadTaxCoefficient);
    }

    public String getString() {
        return name + "," + "'" + roadTaxCoefficient + "'";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRoadTaxCoefficient() {
        return roadTaxCoefficient;
    }

    public void setRoadTaxCoefficient(double roadTaxCoefficient) {
        this.roadTaxCoefficient = roadTaxCoefficient;
    }

}
