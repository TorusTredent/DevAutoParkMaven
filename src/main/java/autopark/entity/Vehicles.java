package autopark.entity;

import autopark.collection.VehicleCollection;
import autopark.entity.engine.Startable;
import autopark.exception.NotVehicleException;
import autopark.infrastructure.core.annotations.InitMethod;
import autopark.infrastructure.dto.annotations.Column;
import autopark.infrastructure.dto.annotations.ID;
import autopark.infrastructure.dto.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static autopark.console.Writer.printError;
import static autopark.entity.engine.EngineCreator.create;
import static autopark.service.TechnicalSpecialist.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicles")
public class Vehicles implements Comparable<Vehicles> {

    @ID
    private Long id;

    @Column(name = "typeId")
    private Long typeId;

    @Column(name = "model", unique = true)
    private String modelName;

    @Column(name = "regNumber", unique = true)
    private String regNumber;

    @Column(name = "weight", unique = true)
    private Double weight;

    @Column(name = "year", unique = true)
    private Integer year;

    @Column(name = "mileage", unique = true)
    private Integer mileage;

    @Column(name = "color", unique = true)
    private String color;

    @Column(name = "engineName")
    private String engineName;

    @Column(name = "consumptionPerKilometer")
    private Double consumptionPerKilometer;

    @Column(name = "tankCapacity")
    private Double tankCapacity;

    @Column(name = "engineCapacity", nullable = false)
    private Double engineCapacity;

    private Startable startable;
    private Map<Long, Types> typeIdToType;

    public Vehicles(Long id, Long typeId, String modelName, String regNumber, Double weight, Integer year, Integer mileage,
                    String color, String engineName, Double consumptionPerKilometer, Double tankCapacity, Double engineCapacity,
                    Startable startable) {
        this.id = id;
        this.typeId = typeId;
        this.consumptionPerKilometer = consumptionPerKilometer;
        this.tankCapacity = tankCapacity;
        this.engineCapacity = engineCapacity;
        try {
            if (validateModelName(modelName)) {
                this.modelName = modelName;
            } else throw new NotVehicleException("Wrong model name: " + modelName);
            if (validateRegistrationNumber(regNumber)) {
                this.regNumber = regNumber;
            } else throw new NotVehicleException("Wrong reg number: " + regNumber);
            if (validateWeight(weight)) {
                this.weight = weight;
            } else throw new NotVehicleException("Wrong weight: " + weight);
            if (validateManufactureYear(year)) {
                this.year = year;
            } else throw new NotVehicleException("Wrong manufacture year: " + year);
            if (validateMileage(mileage)) {
                this.mileage = mileage;
            } else throw new NotVehicleException("Wrong mileage: " + mileage);
            if (validateColor(color)) {
                this.color = color;
            } else throw new NotVehicleException("Wrong color: " + color);
            if (validateEngineName(engineName)) {
                this.engineName = engineName;
            } else throw new NotVehicleException("Wrong engine name" + engineName);
        } catch (NotVehicleException e) {
            printError(e.getMessage());
        }
        this.startable = create(this);
    }

    @InitMethod
    public void init() {
        try {
            if (validateEngineName(this.engineName)) {
                startable = create(this);
            } else throw new NotVehicleException("Wrong engine name" + engineName);
        } catch (NotVehicleException e) {
            printError(e.getMessage());
        }
        initializeTypeIdToType();
    }

    public void setModelName(String modelName) {
        try {
            if (validateModelName(modelName)) {
                this.modelName = modelName;
            } else throw new NotVehicleException("Wrong model name: " + modelName);
        } catch (NotVehicleException e) {
            printError(e);
        }
    }

    public void setRegNumber(String regNumber) {
        try {
            if (validateRegistrationNumber(regNumber)) {
                this.regNumber = regNumber;
            } else throw new NotVehicleException("Wrong reg number: " + regNumber);
        } catch (NotVehicleException e) {
            printError(e);
        }
    }

    public void setWeight(Double weight) {
        try {
            if (validateWeight(weight)) {
                this.weight = weight;
            } else throw new NotVehicleException("Wrong model name: " + modelName);
        } catch (NotVehicleException e) {
            printError(e);
        }
    }

    public void setYear(Integer year) {
        try {
            if (validateManufactureYear(year)) {
                this.year = year;
            } else throw new NotVehicleException("Wrong manufacture year: " + year);
        } catch (NotVehicleException e) {
            printError(e);
        }
    }

    public void setMileage(Integer mileage) {
        try {

            if (validateMileage(mileage)) {
                this.mileage = mileage;
            } else throw new NotVehicleException("Wrong mileage: " + mileage);
        } catch (NotVehicleException e) {
            printError(e);
        }
    }

    public void setColor(String color) {
        try {
            if (validateColor(color)) {
                this.color = color;
            } else throw new NotVehicleException("Wrong color: " + color);
        } catch (NotVehicleException e) {
            printError(e);
        }
    }

    public double getCalcTaxPerMonth() {
        return (weight * 0.0013) + (startable.getTaxPerMonth() *
                typeIdToType.get(typeId).getCoefTaxes() * 30) + 5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicles vehicles = (Vehicles) o;
        return Objects.equals(typeId, vehicles.typeId) && Objects.equals(modelName, vehicles.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId, modelName);
    }

    @Override
    public int compareTo(Vehicles o) {
        if (Objects.equals(this.getYear(), o.year)) {
            return this.mileage - o.mileage;
        } else {
            return this.year - o.year;
        }
    }

    public double getTotallIncom(VehicleCollection vehicleCollection) {
        List<Rents> rents = vehicleCollection.getRentsListForVehicle(id);
        return rents.stream()
                .mapToDouble(Rents::getCost)
                .sum();
    }

    public double getTotalProfit(VehicleCollection vehicleCollection) {
        return getTotallIncom(vehicleCollection ) - getCalcTaxPerMonth();
    }


    private void initializeTypeIdToType() {
        typeIdToType = new HashMap<>();
        typeIdToType.put(1L, new Types(1L, "Bus", 1.2d));
        typeIdToType.put(2L, new Types(2L, "Car", 1.0d));
        typeIdToType.put(3L, new Types(3L, "Rink", 1.5d));
        typeIdToType.put(4L, new Types(4L, "Tractor", 1.3d));
    }
}
