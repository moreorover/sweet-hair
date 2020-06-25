package martin.sweethair.repository;

import martin.sweethair.model.OrderProducts;
import martin.sweethair.model.OrderProductsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProducts, OrderProductsId> {
}
