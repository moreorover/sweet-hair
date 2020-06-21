package martin.sweethair.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Embeddable
public class OrderProductsId implements Serializable {

    private Long orderId;
    private Long productId;

    @Override
    public String toString() {
        return String.format("%s_%s", getOrderId(), getProductId());
    }
}
