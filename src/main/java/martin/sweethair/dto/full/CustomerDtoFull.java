package martin.sweethair.dto.full;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import martin.sweethair.dto.base.SaleDtoBase;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDtoFull {

    private Long id;
    private String name;

    private Set<SaleDtoBase> sales;
}
