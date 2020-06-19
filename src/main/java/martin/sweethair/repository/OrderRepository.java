package martin.sweethair.repository;

import martin.sweethair.data.models.Order;
import martin.sweethair.data.projections.OrderCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "orders", path = "orders",
        excerptProjection = OrderCard.class
)
public interface OrderRepository extends JpaRepository<Order, Long> {

    <T> List<T> findAllBy(Class<T> type);

    <T> T findFirstById(long id, Class<T> type);
}
