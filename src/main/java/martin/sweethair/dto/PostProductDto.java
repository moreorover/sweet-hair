package martin.sweethair.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.ClientTypeDtoBase;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostProductDto {

    private String name;
    private int inStockCount;
    private int size;
    private String sizeUnit;
    private double price;
    private double totalSpent;
    private double totalReceived;
    private double profit;
}
