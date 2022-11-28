package autopark.infrastructure.dto;

import java.util.List;
import java.util.Optional;

public interface EntityManager {

    <T> Optional<T> get(long id, Class<T> clazz);

    void save(Object object);

    <T> List<T> getAll(Class<T> clazz);
}
