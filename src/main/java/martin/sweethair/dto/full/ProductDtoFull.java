package martin.sweethair.dto.full;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.OperationDtoBase;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoFull {

    private Long id;
    private String name;
    private int inStockCount;
    private int size;
    private String sizeUnit;
    private double price;
    private double totalSpent;
    private double totalReceived;
    private double profit;

    private List<OperationDtoBase> operations;
}
