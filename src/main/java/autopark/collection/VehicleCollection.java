package autopark.collection;

import autopark.entity.Rents;
import autopark.entity.Types;
import autopark.entity.Vehicles;
import autopark.infrastructure.core.annotations.Autowired;
import autopark.infrastructure.core.annotations.InitMethod;
import autopark.parser.ParserVehiclesFromDB;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class VehicleCollection {

    private List<Vehicles> vehiclesList;
    private List<Types> typesList;
    private List<Rents> rentsList;

    @Autowired
    private ParserVehiclesFromDB parserVehiclesFromDB;

    @InitMethod
    public void init() {
        rentsList = parserVehiclesFromDB.loadRentsList();
        typesList = parserVehiclesFromDB.loadTypesList();
        vehiclesList = parserVehiclesFromDB.loadVehiclesList();
    }

    public List<Rents> getRentsListForVehicle(Long id) {
        return rentsList.stream()
                .filter(value -> Objects.equals(value.getVehicleId(), id))
                .collect(Collectors.toList());
    }
}
