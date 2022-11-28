package autopark.service.entity;

import autopark.entity.Types;
import autopark.infrastructure.core.annotations.Autowired;
import autopark.infrastructure.dto.EntityManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TypesService {

    @Autowired
    private EntityManager entityManager;

    public Types get(Long id) {
        return entityManager.get(id, Types.class).orElse(new Types());
    }

    public List<Types> getAll() {
        return entityManager.getAll(Types.class);
    }

    public void save(Types types) {
        entityManager.save(types);
    }
}
