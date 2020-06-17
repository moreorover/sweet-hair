package martin.sweethair.repository;

import martin.sweethair.data.models.OrderItems;
import martin.sweethair.data.models.OrderItemsId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "order_items", path = "order_items")
public interface OrderItemsRepository extends CrudRepository<OrderItems, OrderItemsId> {
}
