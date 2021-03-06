package martin.sweethair.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoBase {

    private Long id;
    private String name;
    private int inStockCount;
    private int size;
    private String sizeUnit;
}
