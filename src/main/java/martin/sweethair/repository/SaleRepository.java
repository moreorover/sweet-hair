package martin.sweethair.repository;

import martin.sweethair.data.Sale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "sales", path = "sales")
public interface SaleRepository extends CrudRepository<Sale, Long> {
}
