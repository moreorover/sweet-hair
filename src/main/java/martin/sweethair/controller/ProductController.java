package martin.sweethair.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.PostClientDto;
import martin.sweethair.dto.PostProductDto;
import martin.sweethair.dto.base.ProductDtoBase;
import martin.sweethair.dto.full.ClientDtoFull;
import martin.sweethair.dto.full.ProductDtoFull;
import martin.sweethair.model.Client;
import martin.sweethair.model.Product;
import martin.sweethair.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
@Slf4j
public class ProductController {

    private final ModelMapper modelMapper;

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDtoFull> create(@RequestBody PostProductDto postProductDto) {
        Product saved = productService.save(postProductDto);
        return new ResponseEntity<>(modelMapper.map(saved, ProductDtoFull.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<ProductDtoFull>> getAll() {
        Set<Product> products = productService.getAll();
        Set<ProductDtoFull> productDtoFulls = products.stream()
                .map(client -> modelMapper.map(client, ProductDtoFull.class))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(productDtoFulls, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDtoFull> getById(@PathVariable("id") Long id) {
        Product product = productService.getById(id);
        ProductDtoFull productDtoFull = modelMapper.map(product, ProductDtoFull.class);
        return new ResponseEntity<>(productDtoFull, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDtoFull> update(@PathVariable("id") Long id, @RequestBody ProductDtoBase productDtoBase) {
        if (!Objects.equals(id, productDtoBase.getId())){
            // if different
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product updatedProduct = productService.update(productDtoBase);
        ProductDtoFull productDtoFullUpdated = modelMapper.map(updatedProduct, ProductDtoFull.class);
        return new ResponseEntity<>(productDtoFullUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (productService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
