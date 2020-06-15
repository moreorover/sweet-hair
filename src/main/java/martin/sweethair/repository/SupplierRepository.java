package martin.sweethair.repository;

import martin.sweethair.data.models.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(
        collectionResourceRel = "suppliers",
        path = "suppliers"
//        excerptProjection= SupplierProjection.class
)
public interface SupplierRepository extends CrudRepository<Supplier, Long> {
}
