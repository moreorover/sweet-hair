package martin.sweethair.data.projections;

import martin.sweethair.data.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "ProductProjection", types = { Product.class })
public interface ProductProjection {

    @Value("#{target.id}")
    long getId();

    String getName();

//    @Value("#{target.getSupplier().getId()}")
//    int getSupplierId();
}
