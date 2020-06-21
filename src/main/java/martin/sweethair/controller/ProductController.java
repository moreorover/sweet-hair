package martin.sweethair.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.ProductDtoBase;
import martin.sweethair.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDtoBase> createProduct(@RequestBody ProductDtoBase productDtoBase) {
        ProductDtoBase savedProduct = productService.save(productDtoBase);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<ProductDtoBase>> getAllProducts() {
        Set<ProductDtoBase> productDtos = productService.getAll();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDtoBase> getProductById(@PathVariable("id") Long id) {
        ProductDtoBase productDtoBase = productService.getProductById(id);
        return new ResponseEntity<>(productDtoBase, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDtoBase> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDtoBase productDtoBase) {
        if (!Objects.equals(id, productDtoBase.getId())){
            // if different
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ProductDtoBase updatedProduct = productService.updateProduct(productDtoBase);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
