package martin.sweethair.dto.full;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.OperationDtoBase;
import martin.sweethair.dto.base.TransactionTypeDtoBase;
import martin.sweethair.model.Operation;
import martin.sweethair.model.TransactionType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTypeDtoFull {

    private Long id;
    private String name;

    private TransactionTypeDtoBase transactionType;
    private OperationDtoBase operation;
}