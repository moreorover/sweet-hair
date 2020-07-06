package martin.sweethair.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationDtoBase {

    private Long id;
    private LocalDate date;
    private Double total;
    private Double profit;
    private String note;
}
