package martin.sweethair.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDtoBase implements DtoBase {

    private Long id;
    private boolean paid;
    private double amount;
    private String note;
    private LocalDate date;
}
