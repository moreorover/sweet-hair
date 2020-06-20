package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.ProductDto;
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
    public ProductDto save(ProductDto productDto) {
        Product save = productRepository.save(modelMapper.map(productDto, Product.class));
        productDto.setId(save.getId());
        return productDto;
    }

    @Transactional(readOnly = true)
    public Set<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toSet());
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No product found with ID -> " + id));
        return modelMapper.map(product, ProductDto.class);
    }

    public ProductDto updateProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new SpringDataException("No product found with ID -> " + productDto.getId()));;

        product.setName(productDto.getName());
        productRepository.save(product);
        return modelMapper.map(product, ProductDto.class);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
