package autopark.dto.service;

import autopark.collection.VehicleCollection;
import autopark.dto.RentsDto;
import autopark.dto.TypesDto;
import autopark.dto.VehiclesDto;
import autopark.infrastructure.core.annotations.Autowired;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class DtoService {
    @Autowired
    private VehicleCollection vehicleCollection;

    public DtoService() {
    }

    public List<VehiclesDto> getVehicles() {
        return vehicleCollection.getVehiclesList().stream()
                .map(vehicle -> {
                    return VehiclesDto.builder()
                            .id(vehicle.getId())
                            .typeId(vehicle.getTypeId())
                            .typeName(vehicleCollection.getTypesList().get((int) (vehicle.getTypeId() - 1)).getName())
                            .coefTaxes(vehicleCollection.getTypesList().get((int) (vehicle.getTypeId() - 1)).getCoefTaxes())
                            .model(vehicle.getModelName())
                            .stateNumber(vehicle.getRegNumber())
                            .weight(vehicle.getWeight())
                            .year(vehicle.getYear())
                            .mileage(vehicle.getMileage())
                            .color(vehicle.getColor())
                            .engineName(vehicle.getEngineName())
                            .tankCapacity(vehicle.getTankCapacity())
                            .engineCapacity(vehicle.getEngineCapacity())
                            .per100Kilometers(vehicle.getConsumptionPerKilometer() * 100)
                            .maxKilometers(vehicle.getStartable().getMaxKilometers())
                            .tax(vehicle.getCalcTaxPerMonth())
                            .income(vehicle.getTotallIncom(vehicleCollection))
                            .profit(vehicle.getTotalProfit(vehicleCollection))
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<TypesDto> getTypes() {
        return vehicleCollection.getTypesList().stream()
                .map(types -> {
                    return TypesDto.builder()
                            .id(types.getId())
                            .name(types.getName())
                            .coefTaxes(types.getCoefTaxes())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<RentsDto> getRents() {
        return vehicleCollection.getRentsList().stream()
                .map(rents -> {
                    return RentsDto.builder()
                            .id(rents.getId())
                            .vehicleId(rents.getVehicleId())
                            .date(rents.getDate())
                            .cost(rents.getCost())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public static double calculateAverageTax(List<VehiclesDto> dtoList) {
        double sum = 0.0d;
        double count = 0.0d;

        for (VehiclesDto v:
                dtoList) {
            sum += v.getTax();
            count++;
        }

        return (sum / count);
    }

    public static double calculateAverageIncome(List<VehiclesDto> dtoList) {
        double sum = 0.0d;
        double count = 0.0d;

        for (VehiclesDto v:
                dtoList) {
            sum += v.getIncome();
            count++;
        }

        return (sum / count);
    }

    public static double calculateTotalProfit(List<VehiclesDto> dtoList) {
        double sum = 0.0d;
        for (VehiclesDto v: dtoList) {
            sum += v.getProfit();
        }
        return sum;
    }
}