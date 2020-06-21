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
public class ProductService {

    private final ModelMapper modelMapper;

    private final ProductRepository productRepository;

    @Transactional
    public ProductDtoBase save(ProductDtoBase productDtoBase) {
        Product save = productRepository.save(modelMapper.map(productDtoBase, Product.class));
        productDtoBase.setId(save.getId());
        return productDtoBase;
    }

    @Transactional(readOnly = true)
    public Set<ProductDtoBase> getAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDtoBase.class))
                .collect(Collectors.toSet());
    }

    public ProductDtoBase getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No product found with ID -> " + id));
        return modelMapper.map(product, ProductDtoBase.class);
    }

    public ProductDtoBase updateProduct(ProductDtoBase productDtoBase) {
        Product product = productRepository.findById(productDtoBase.getId())
                .orElseThrow(() -> new SpringDataException("No product found with ID -> " + productDtoBase.getId()));

        product.setName(productDtoBase.getName());
        productRepository.save(product);
        return modelMapper.map(product, ProductDtoBase.class);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
