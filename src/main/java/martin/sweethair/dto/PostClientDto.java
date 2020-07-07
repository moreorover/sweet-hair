package martin.sweethair.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.ClientTypeDtoBase;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostClientDto {

    private String name;
    private ClientTypeDtoBase clientType;
}
