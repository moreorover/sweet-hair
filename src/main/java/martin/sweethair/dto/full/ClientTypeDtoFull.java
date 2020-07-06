package martin.sweethair.dto.full;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.ClientDtoBase;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientTypeDtoFull {

    private Long id;
    private String name;

    private List<ClientDtoBase> clients;
}
