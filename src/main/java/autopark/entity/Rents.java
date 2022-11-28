package autopark.entity;

import autopark.exception.NotVehicleException;
import autopark.infrastructure.dto.annotations.Column;
import autopark.infrastructure.dto.annotations.ID;
import autopark.infrastructure.dto.annotations.Table;
import autopark.service.TechnicalSpecialist;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static autopark.console.Writer.printError;
import static autopark.service.TechnicalSpecialist.validateCost;

@Builder
@Data
@NoArgsConstructor
@Table(name = "rents")
public class Rents {

    @ID
    private Long id;

    @Column(name = "vehicleId")
    private Long vehicleId;

    @Column(name = "date")
    private Date date;

    @Column(name = "cost")
    private Double cost;

    public Rents(Long id, Long vehicleId, Date date, Double cost) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.date = date;
        try {
            if (validateCost(cost)) {
                this.cost = cost;
            } else throw new NotVehicleException("Vehicle cost is wrong" + cost);
        } catch (NotVehicleException e) {
            printError(e.getMessage());
        }
    }

    public void setCost(Double cost) {
        try {
            if (validateCost(cost)) {
                this.cost = cost;
            } else throw new NotVehicleException("Vehicle cost is wrong" + cost);
        } catch (NotVehicleException e) {
            printError(e.getMessage());
        }
    }
}
