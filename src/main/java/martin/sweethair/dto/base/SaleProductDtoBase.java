package martin.sweethair.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleProductDtoBase {

    private int quantity;
    private double unitPrice;

    private ProductDtoBase product;
}
