package martin.sweethair.repository;

import martin.sweethair.data.models.Order;
import martin.sweethair.data.projections.OrderCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "orders", path = "orders",
        excerptProjection = OrderCard.class)
public interface OrderRepository extends CrudRepository<Order, Long> {
}
