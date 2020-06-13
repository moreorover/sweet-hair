package martin.sweethair.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "suppliers")
public class Supplier extends BaseEntity {

    private String name;
    private String url;
    private String logo;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Order> orders;
}
