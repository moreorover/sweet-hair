package martin.sweethair.dto.full;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.CustomerDtoBase;
import martin.sweethair.dto.base.OrderProductDtoBase;
import martin.sweethair.dto.base.SaleProductDtoBase;
import martin.sweethair.dto.base.SupplierDtoBase;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDtoFull {

    private Long id;
    private LocalDate soldAt;
    private double total;
    private int itemsCount;
    private String currency;

    private CustomerDtoBase customer;

    private Set<SaleProductDtoBase> products;
}
