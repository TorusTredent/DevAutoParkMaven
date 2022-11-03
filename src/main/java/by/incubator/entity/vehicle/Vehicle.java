package by.incubator.entity.vehicle;

import by.incubator.console.Writer;
import by.incubator.entity.Rent;
import by.incubator.entity.engine.Startable;
import by.incubator.entity.enums.Color;
import by.incubator.exception.NotVehicleException;

import java.util.List;
import java.util.Objects;

import static by.incubator.entity.TechnicalSpecialist.*;

public class Vehicle implements Comparable<Vehicle> {

    private int id;
    private VehicleType vehicleType;
    private String modelName;
    private String regNumber;
    private int weight;
    private int manufactureYear;
    private int mileage;
    private Color color;
    private double tankCapacity;
    private Startable startable;
    private List<Rent> rents;

    public Vehicle() {
    }

    public Vehicle(VehicleType vehicleType, String modelName, String regNumber, int weight, int manufactureYear, int mileage, Color color) {
        try {
            if (validateVehicleType(vehicleType)) {
                this.vehicleType = vehicleType;
            } else throw new NotVehicleException("Wrong vehicle type: " + vehicleType);
            if (validateModelName(modelName)) {
                this.modelName = modelName;
            } else throw new NotVehicleException("Wrong model name: " + modelName);
            if (validateRegistrationNumber(regNumber)) {
                this.regNumber = regNumber;
            } else throw new NotVehicleException("Wrong reg number: " + regNumber);
            if (validateWeight(weight)) {
                this.weight = weight;
            } else throw new NotVehicleException("Wrong weight: " + weight);
            if (validateManufactureYear(manufactureYear)) {
                this.manufactureYear = manufactureYear;
            } else throw new NotVehicleException("Wrong manufacture year: " + manufactureYear);
            if (validateMileage(mileage)) {
                this.mileage = mileage;
            } else throw new NotVehicleException("Wrong mileage: " + mileage);
            if (validateColor(color)) {
                this.color = color;
            } else throw new NotVehicleException("Wrong color: " + color);
        } catch (NotVehicleException e) {
            Writer.printError(e.getMessage());
        }
    }

    public Vehicle(VehicleType vehicleType, Startable startable, String modelName, String regNumber, int weight,
                   int manufactureYear, int mileage, Color color) {
        try {
            if (validateVehicleType(vehicleType)) {
                this.vehicleType = vehicleType;
            } else throw new NotVehicleException("Wrong vehicle type: " + vehicleType);
            if (validateModelName(modelName)) {
                this.modelName = modelName;
            } else throw new NotVehicleException("Wrong model name: " + modelName);
            if (validateRegistrationNumber(regNumber)) {
                this.regNumber = regNumber;
            } else throw new NotVehicleException("Wrong reg number: " + regNumber);
            if (validateWeight(weight)) {
                this.weight = weight;
            } else throw new NotVehicleException("Wrong weight: " + weight);
            if (validateManufactureYear(manufactureYear)) {
                this.manufactureYear = manufactureYear;
            } else throw new NotVehicleException("Wrong manufacture year: " + manufactureYear);
            if (validateMileage(mileage)) {
                this.mileage = mileage;
            } else throw new NotVehicleException("Wrong mileage: " + mileage);
            if (validateColor(color)) {
                this.color = color;
            } else throw new NotVehicleException("Wrong color: " + color);
            this.startable = startable;
        } catch (NotVehicleException e) {
            Writer.printError(e.getMessage());
        }
    }

    public Vehicle(int id, VehicleType vehicleType, String modelName, String regNumber, int weight,
                   int manufactureYear, int mileage, Color color, Startable startable, List<Rent> rents) {
        this.id = id;
        try {
            if (validateVehicleType(vehicleType)) {
                this.vehicleType = vehicleType;
            } else throw new NotVehicleException("Wrong vehicle type: " + vehicleType);
            if (validateModelName(modelName)) {
                this.modelName = modelName;
            } else throw new NotVehicleException("Wrong model name: " + modelName);
            if (validateRegistrationNumber(regNumber)) {
                this.regNumber = regNumber;
            } else throw new NotVehicleException("Wrong reg number: " + regNumber);
            if (validateWeight(weight)) {
                this.weight = weight;
            } else throw new NotVehicleException("Wrong weight: " + weight);
            if (validateManufactureYear(manufactureYear)) {
                this.manufactureYear = manufactureYear;
            } else throw new NotVehicleException("Wrong manufacture year: " + manufactureYear);
            if (validateMileage(mileage)) {
                this.mileage = mileage;
            } else throw new NotVehicleException("Wrong mileage: " + mileage);
            if (validateColor(color)) {
                this.color = color;
            } else throw new NotVehicleException("Wrong color: " + color);
            this.startable = startable;
            this.rents = rents;
        } catch (NotVehicleException e) {
            Writer.printError(e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        if (validateModelName(modelName)) {
            this.modelName = modelName;
        } else {
            try {
                throw new NotVehicleException("Wrong model name: " + modelName);
            } catch (NotVehicleException e) {
                Writer.printError(e);
            }
        }
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        if (validateRegistrationNumber(regNumber)) {
            this.regNumber = regNumber;
        } else {
            try {
                throw new NotVehicleException("Wrong reg number: " + regNumber);
            } catch (NotVehicleException e) {
                Writer.printError(e);
            }
        }
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        if (validateWeight(weight)) {
            this.weight = weight;
        } else {
            try {
                throw new NotVehicleException("Wrong model name: " + modelName);
            } catch (NotVehicleException e) {
                Writer.printError(e);
            }
        }
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        if (validateManufactureYear(manufactureYear)) {
            this.manufactureYear = manufactureYear;
        } else {
            try {
                throw new NotVehicleException("Wrong manufacture year: " + manufactureYear);
            } catch (NotVehicleException e) {
                Writer.printError(e);
            }
        }
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        if (validateMileage(mileage)) {
            this.mileage = mileage;
        } else {
            try {
                throw new NotVehicleException("Wrong mileage: " + mileage);
            } catch (NotVehicleException e) {
                Writer.printError(e);
            }
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (validateColor(color)) {
            this.color = color;
        } else {
            try {
                throw new NotVehicleException("Wrong color: " + color);
            } catch (NotVehicleException e) {
                Writer.printError(e);
            }
        }
    }

    public double getTankCapacity() {
        return tankCapacity;
    }

    public void setTankCapacity(double tankCapacity) {
        this.tankCapacity = tankCapacity;
    }

    public Startable getStartable() {
        return startable;
    }

    public void setStartable(Startable startable) {
        this.startable = startable;
    }

    public double getCalcTaxPerMonth() {
        return (weight * 0.0013) + (startable.getTaxPerMonth() *
                vehicleType.getRoadTaxCoefficient() * 30) + 5;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        if (validateVehicleType(vehicleType)) {
            this.vehicleType = vehicleType;
        } else {
            try {
                throw new NotVehicleException("Wrong vehicle type: " + vehicleType);
            } catch (NotVehicleException e) {
                Writer.printError(e.getMessage());
            }
        }
    }

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
    }

    @Override
    public String toString() {
        return vehicleType.getString() + ", "
                + modelName + ", "
                + regNumber + ", "
                + regNumber + ", "
                + weight + ", "
                + manufactureYear + ", "
                + mileage + ", "
                + color + ", "
                + tankCapacity + ", '"
                + getCalcTaxPerMonth() + "', "
                + startable.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicleType, vehicle.vehicleType) && Objects.equals(modelName, vehicle.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleType, modelName);
    }

    @Override
    public int compareTo(Vehicle o) {
        if (this.getManufactureYear() == o.manufactureYear) {
            return this.mileage - o.mileage;
        } else {
            return this.manufactureYear - o.manufactureYear;
        }
    }

    public double getTotallncom() {
        return rents.stream()
                .mapToDouble(Rent::getRentalPrice)
                .sum();
    }

    public double getTotalProfit() {
        return getTotallncom() - getCalcTaxPerMonth();
    }
}
