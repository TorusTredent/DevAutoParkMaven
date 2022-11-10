package autopark.service.entity;

import autopark.entity.Vehicles;
import autopark.infrastructure.core.annotations.Autowired;
import autopark.infrastructure.dto.EntityManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VehiclesService {

    @Autowired
    private EntityManager entityManager;

    public Vehicles get(Long id) {
        return entityManager.get(id, Vehicles.class).orElse(new Vehicles());
    }

    public List<Vehicles> getAll() {
        return entityManager.getAll(Vehicles.class);
    }

    public void save(Vehicles vehicles) {
        entityManager.save(vehicles);
    }
}
