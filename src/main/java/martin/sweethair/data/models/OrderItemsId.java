package martin.sweethair.data.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class OrderItemsId implements Serializable {

    private long order;
    private long product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemsId that = (OrderItemsId) o;
        return order == that.order &&
                product == that.product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}
