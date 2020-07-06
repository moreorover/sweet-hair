package martin.sweethair.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.ClientTypeDtoBase;
import martin.sweethair.dto.base.ProductDtoBase;
import martin.sweethair.model.ClientType;
import martin.sweethair.service.ClientTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/client_type")
@AllArgsConstructor
@Slf4j
public class ClientTypeController {

    private final ClientTypeService clientTypeService;

    @PostMapping
    public ResponseEntity<ClientTypeDtoBase> create(@RequestBody ClientTypeDtoBase clientTypeDtoBase) {
        ClientTypeDtoBase saved = clientTypeService.save(clientTypeDtoBase);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<ClientTypeDtoBase>> getAll() {
        Set<ClientTypeDtoBase> clientTypeDtoBases = clientTypeService.getAll();
        return new ResponseEntity<>(clientTypeDtoBases, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientTypeDtoBase> getById(@PathVariable("id") Long id) {
        ClientTypeDtoBase clientTypeDtoBase = clientTypeService.getById(id);
        return new ResponseEntity<>(clientTypeDtoBase, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientTypeDtoBase> updateProduct(@PathVariable("id") Long id, @RequestBody ClientTypeDtoBase clientTypeDtoBase) {
        if (!Objects.equals(id, clientTypeDtoBase.getId())){
            // if different
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ClientTypeDtoBase updatedClientTypeDtoBase = clientTypeService.update(clientTypeDtoBase);
        return new ResponseEntity<>(updatedClientTypeDtoBase, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        if (clientTypeService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
