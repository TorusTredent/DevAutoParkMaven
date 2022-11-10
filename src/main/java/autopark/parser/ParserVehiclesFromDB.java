package autopark.parser;

import autopark.entity.Rents;
import autopark.entity.Types;
import autopark.entity.Vehicles;
import autopark.infrastructure.core.annotations.Autowired;
import autopark.service.entity.RentsService;
import autopark.service.entity.TypesService;
import autopark.service.entity.VehiclesService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ParserVehiclesFromDB {

    @Autowired
    private VehiclesService vehiclesService;

    @Autowired
    private TypesService typesService;

    @Autowired
    private RentsService rentsService;

    public List<Types> loadTypesList() {
        return typesService.getAll();
    }

    public List<Rents> loadRentsList() {
        return rentsService.getAll();
    }

    public List<Vehicles> loadVehiclesList() {
        return vehiclesService.getAll();
    }
}
