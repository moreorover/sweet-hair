package martin.sweethair.repository;

import martin.sweethair.model.SaleProducts;
import martin.sweethair.model.SaleProductsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleProductsRepository extends JpaRepository<SaleProducts, SaleProductsId> {
}
