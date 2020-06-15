package martin.sweethair.data.projections;

import martin.sweethair.data.models.Supplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "SupplierProjection", types = { Supplier.class })
public interface SupplierProjection {

    @Value("#{target.id}")
    long getId();

    String getName();
    String getUrl();
    String getLogo();

//    List<ProductProjection> getProducts();

//    @Value("#{target.getProducts().size()}")
//    int getProductsCount();
}
