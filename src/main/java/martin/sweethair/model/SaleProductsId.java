package martin.sweethair.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Embeddable
public class SaleProductsId implements Serializable {

    private Long saleId;
    private Long productId;

    @Override
    public String toString() {
        return String.format("%s_%s", getSaleId(), getProductId());
    }
}
