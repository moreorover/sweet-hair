package martin.sweethair.dto.full;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.ClientDtoBase;
import martin.sweethair.dto.base.OperationDtoBase;
import martin.sweethair.model.Client;
import martin.sweethair.model.Operation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientTypeDtoFull {

    private Long id;
    private String name;

    private List<ClientDtoBase> clients;
}
