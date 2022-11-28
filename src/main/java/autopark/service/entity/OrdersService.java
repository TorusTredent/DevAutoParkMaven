package autopark.service.entity;

import autopark.entity.Orders;
import autopark.infrastructure.core.annotations.Autowired;
import autopark.infrastructure.dto.EntityManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrdersService {

    @Autowired
    private EntityManager entityManager;

    public Orders get(Long id) {
        return entityManager.get(id, Orders.class).orElse(new Orders());
    }

    public List<Orders> getAll() {
        return entityManager.getAll(Orders.class);
    }

    public void save(Orders orders) {
        entityManager.save(orders);
    }
}
