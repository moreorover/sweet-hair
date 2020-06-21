package martin.sweethair.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.SupplierDtoBase;
import martin.sweethair.dto.full.SupplierDtoFull;
import martin.sweethair.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/supplier")
@AllArgsConstructor
@Slf4j
public class SupplierController {

    private final SupplierService productService;

    @PostMapping
    public ResponseEntity<SupplierDtoBase> createSupplier(@RequestBody SupplierDtoBase supplierDto) {
        SupplierDtoBase savedSupplier = productService.save(supplierDto);
        return new ResponseEntity<>(savedSupplier, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<SupplierDtoFull>> getAllSuppliers() {
        Set<SupplierDtoFull> supplierDtos = productService.getAll();
        return new ResponseEntity<>(supplierDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDtoFull> getSupplierById(@PathVariable("id") Long id) {
        SupplierDtoFull supplierDto = productService.getSupplierById(id);
        return new ResponseEntity<>(supplierDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SupplierDtoBase> updateSupplier(@PathVariable("id") Long id, @RequestBody SupplierDtoBase supplierDto) {
        if (!Objects.equals(id, supplierDto.getId())){
            // if different
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SupplierDtoBase updatedProduct = productService.updateSupplier(supplierDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
