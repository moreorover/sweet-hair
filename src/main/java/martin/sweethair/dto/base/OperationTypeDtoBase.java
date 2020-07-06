package martin.sweethair.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationTypeDtoBase {

    private Long id;
    private String name;
}
