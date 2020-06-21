package martin.sweethair.dto.full;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.OrderDtoBase;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDtoFull {

    private Long id;
    private String name;
    private String url;
    private String logo;

    private Set<OrderDtoBase> orders;
}
