package autopark.entity;

import autopark.exception.NotVehicleException;
import autopark.infrastructure.dto.annotations.Column;
import autopark.infrastructure.dto.annotations.ID;
import autopark.infrastructure.dto.annotations.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static autopark.console.Writer.printError;
import static autopark.service.TechnicalSpecialist.validateCoefTaxes;
import static autopark.service.TechnicalSpecialist.validateTypeName;

@Builder
@Data
@NoArgsConstructor
@Table(name = "types")
public class Types {

    @ID
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "coefTaxes")
    private Double coefTaxes;

    public Types(Long id, String name, Double coefTaxes) {
        this.id = id;
        try {
            if (validateTypeName(name)) {
                this.name = name;
            } else throw new NotVehicleException("Vehicle type name is wrong" + name);
            if (validateCoefTaxes(coefTaxes)) {
                this.coefTaxes = coefTaxes;
            } else throw new NotVehicleException("Vehicle type coefTaxes is wrong" + coefTaxes);
        } catch (NotVehicleException e) {
            printError(e.getMessage());
        }
    }

    public void setName(String name) {
        try {
            if (validateTypeName(name)) {
                this.name = name;
            } else throw new NotVehicleException("Vehicle type name is wrong" + name);
        } catch (NotVehicleException e) {
            printError(e.getMessage());
        }
    }

    public void setCoefTaxes(Double coefTaxes) {
        try {
            if (validateCoefTaxes(coefTaxes)) {
                this.coefTaxes = coefTaxes;
            } else throw new NotVehicleException("Vehicle type coefTaxes is wrong" + coefTaxes);
        } catch (NotVehicleException e) {
            printError(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Types types = (Types) o;
        return Double.compare(types.coefTaxes, coefTaxes) == 0 && Objects.equals(types.name, name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coefTaxes);
    }
}


