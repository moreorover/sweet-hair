package martin.sweethair.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.model.Order;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {

    private Long id;
    private String name;
    private String url;
    private String logo;
    private Set<Order> orders;
}
