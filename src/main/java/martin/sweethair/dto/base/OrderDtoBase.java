package martin.sweethair.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoBase {

    private Long id;
    private LocalDate purchasedAt;
    private double total;
    private int itemsCount;
    private String currency;

    private SupplierDtoBase supplier;
}
