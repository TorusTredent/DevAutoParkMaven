package autopark.entity;

import autopark.infrastructure.dto.annotations.Column;
import autopark.infrastructure.dto.annotations.ID;
import autopark.infrastructure.dto.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Orders {

    @ID
    private Long id;

    @Column(name = "vehicleId")
    private Long vehicleId;

    @Column(name = "orderLine")
    private String orderLine;

    public Orders(Long vehicleId, String orderLine) {
        this.vehicleId = vehicleId;
        this.orderLine = orderLine;
    }
}