package martin.sweethair.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDtoBase {

    private int quantity;
    private double unitPrice;

    private ProductDtoBase product;

    public Long getProductId() {
        return this.getProduct().getId();
    }
}
