package autopark.infrastructure.dto.impl;

import autopark.infrastructure.core.Context;
import autopark.infrastructure.core.annotations.Autowired;
import autopark.infrastructure.dto.ConnectionFactory;
import autopark.infrastructure.dto.EntityManager;
import autopark.infrastructure.dto.PostgresDataBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Setter
@Getter
public class EntityManagerImpl implements EntityManager {

    @Autowired
    private ConnectionFactory connection;

    @Autowired
    private PostgresDataBase dataBaseService;


    @Autowired
    private Context context;

    public EntityManagerImpl() {
    }

    @Override
    public <T> Optional<T> get(long id, Class<T> clazz) {
        return dataBaseService.get(id, clazz);
    }

    @Override
    public void save(Object object) {
        dataBaseService.save(object);
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return dataBaseService.getAll(clazz);
    }
}
