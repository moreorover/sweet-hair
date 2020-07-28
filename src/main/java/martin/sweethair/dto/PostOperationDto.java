package martin.sweethair.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostOperationDto {

    private LocalDate date;
    private Double total;
    private Double profit;
    private String note;
    private PostOperationTypeDto operationType;
    private PostClientDto client;

}
