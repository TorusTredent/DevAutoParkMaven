package autopark.service.entity;

import autopark.entity.Rents;
import autopark.infrastructure.core.annotations.Autowired;
import autopark.infrastructure.dto.EntityManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RentsService {

    @Autowired
    private EntityManager entityManager;

    public Rents get(Long id) {
        return entityManager.get(id, Rents.class).orElse(new Rents());
    }

    public List<Rents> getAll() {
        return entityManager.getAll(Rents.class);
    }

    public void save(Rents rents) {
        entityManager.save(rents);
    }
}
