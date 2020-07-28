package martin.sweethair.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTransactionDto {

    private String name;
    private boolean paid;
    private double amount;
    private String note;
    private LocalDate date;
    private PostTransactionTypeDto transactionType;
    private PostOperationDto operation;
}
