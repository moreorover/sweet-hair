package martin.sweethair.dto.full;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.OperationDtoBase;
import martin.sweethair.dto.base.TransactionTypeDtoBase;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDtoFull implements DtoFull {

    private Long id;
    private boolean paid;
    private double amount;
    private String note;
    private LocalDate date;

    private TransactionTypeDtoBase transactionType;
    private OperationDtoBase operation;
}
