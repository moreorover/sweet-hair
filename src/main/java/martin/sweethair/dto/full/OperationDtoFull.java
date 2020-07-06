package martin.sweethair.dto.full;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.ClientDtoBase;
import martin.sweethair.dto.base.OperationTypeDtoBase;
import martin.sweethair.dto.base.ProductDtoBase;
import martin.sweethair.dto.base.TransactionDtoBase;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationDtoFull implements DtoFull {

    private Long id;
    private LocalDate date;
    private Double total;
    private Double profit;
    private String note;

    private OperationTypeDtoBase operationType;
    private ClientDtoBase client;
    private List<ProductDtoBase> products;
    private List<TransactionDtoBase> transactions;
}
