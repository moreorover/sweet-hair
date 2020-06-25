package martin.sweethair.dto.full;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.OrderProductDtoBase;
import martin.sweethair.dto.base.SupplierDtoBase;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoFull {

    private Long id;
    private LocalDate purchasedAt;
    private double total;
    private int itemsCount;
    private String currency;

    private SupplierDtoBase supplier;

    private Set<OrderProductDtoBase> products;
}
