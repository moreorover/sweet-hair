package martin.sweethair.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.CustomerDtoBase;
import martin.sweethair.dto.base.SupplierDtoBase;
import martin.sweethair.dto.full.CustomerDtoFull;
import martin.sweethair.dto.full.SupplierDtoFull;
import martin.sweethair.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDtoBase> createCustomer(@RequestBody CustomerDtoBase customerDtoBase) {
        CustomerDtoBase savedSupplier = customerService.save(customerDtoBase);
        return new ResponseEntity<>(savedSupplier, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<CustomerDtoFull>> getAllCustomers() {
        Set<CustomerDtoFull> customerDtos = customerService.getAll();
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDtoFull> getCustomerById(@PathVariable("id") Long id) {
        CustomerDtoFull customerDto = customerService.getCustomerById(id);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDtoBase> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDtoBase customerDtoBase) {
        if (!Objects.equals(id, customerDtoBase.getId())){
            // if different
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CustomerDtoBase updatedCustomer = customerService.updateCustomer(customerDtoBase);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable("id") Long id) {
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
