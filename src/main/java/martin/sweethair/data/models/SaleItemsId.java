package martin.sweethair.data.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class SaleItemsId implements Serializable {

    private long sale;
    private long product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleItemsId that = (SaleItemsId) o;
        return sale == that.sale &&
                product == that.product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sale, product);
    }
}
