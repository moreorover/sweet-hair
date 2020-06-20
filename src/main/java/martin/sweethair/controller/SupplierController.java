package martin.sweethair.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.ProductDto;
import martin.sweethair.dto.SupplierDto;
import martin.sweethair.service.ProductService;
import martin.sweethair.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
@Slf4j
public class SupplierController {

    private final SupplierService productService;

    @PostMapping
    public ResponseEntity<SupplierDto> createProduct(@RequestBody SupplierDto supplierDto) {
        SupplierDto savedSupplier = productService.save(supplierDto);
        return new ResponseEntity<>(savedSupplier, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<SupplierDto>> getAllProducts() {
        Set<SupplierDto> supplierDtos = productService.getAll();
        return new ResponseEntity<>(supplierDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getProductById(@PathVariable("id") Long id) {
        SupplierDto supplierDto = productService.getProductById(id);
        return new ResponseEntity<>(supplierDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SupplierDto> updateProduct(@PathVariable("id") Long id, @RequestBody SupplierDto supplierDto) {
        if (!Objects.equals(id, supplierDto.getId())){
            // if different
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SupplierDto updatedProduct = productService.updateProduct(supplierDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
