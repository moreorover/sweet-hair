package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.ProductDtoBase;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.Product;
import martin.sweethair.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService implements ControllerService<ProductDtoBase> {

    private final ModelMapper modelMapper;

    private final ProductRepository productRepository;

    @Transactional
    public ProductDtoBase save(ProductDtoBase dtoBase) {
        Product save = productRepository.save(modelMapper.map(dtoBase, Product.class));
        dtoBase.setId(save.getId());
        return dtoBase;
    }

    @Transactional(readOnly = true)
    public Set<ProductDtoBase> getAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDtoBase.class))
                .collect(Collectors.toSet());
    }

    public ProductDtoBase getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No product found with ID -> " + id));
        return modelMapper.map(product, ProductDtoBase.class);
    }

    public ProductDtoBase update(ProductDtoBase dtoBase) {
        Product product = productRepository.findById(dtoBase.getId())
                .orElseThrow(() -> new SpringDataException("No product found with ID -> " + dtoBase.getId()));

        product.setName(dtoBase.getName());
        product.setInStockCount(dtoBase.getInStockCount());
        product.setSize(dtoBase.getSize());
        product.setSizeUnit(dtoBase.getSizeUnit());
        productRepository.save(product);
        return modelMapper.map(product, ProductDtoBase.class);
    }

    public boolean deleteById(Long id) {
        Product product = this.getEntityById(id);

        if (product.getOperations().isEmpty()){
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    private Product getEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No product found with ID -> " + id));
    }
}
