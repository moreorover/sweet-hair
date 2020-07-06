package martin.sweethair.dto.full;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.ClientTypeDtoBase;
import martin.sweethair.dto.base.OperationDtoBase;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDtoFull implements DtoFull {

    private Long id;
    private String name;

    private ClientTypeDtoBase clientType;
    private List<OperationDtoBase> operations;
}
