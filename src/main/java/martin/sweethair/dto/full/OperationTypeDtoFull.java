package martin.sweethair.dto.full;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.OperationDtoBase;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationTypeDtoFull {

    private Long id;
    private String name;

    private List<OperationDtoBase> operations;
}
