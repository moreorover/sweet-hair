package martin.sweethair.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.ClientTypeDtoBase;
import martin.sweethair.dto.full.ClientTypeDtoFull;
import martin.sweethair.model.ClientType;
import martin.sweethair.service.ClientTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/client_type")
@AllArgsConstructor
@Slf4j
public class ClientTypeController {

    private final ModelMapper modelMapper;

    private final ClientTypeService clientTypeService;

    @PostMapping
    public ResponseEntity<ClientTypeDtoBase> create(@RequestBody ClientTypeDtoBase clientTypeDtoBase) {
        ClientType saved = clientTypeService.save(clientTypeDtoBase);
        ClientTypeDtoBase savedClientType = modelMapper.map(saved, ClientTypeDtoBase.class);
        return new ResponseEntity<>(savedClientType, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<ClientTypeDtoBase>> getAll() {
        Set<ClientType> clientTypes = clientTypeService.getAll();
        Set<ClientTypeDtoBase> clientTypeDtoBases = clientTypes.stream()
                .map(clientType -> modelMapper.map(clientType, ClientTypeDtoBase.class))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(clientTypeDtoBases, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientTypeDtoFull> getById(@PathVariable("id") Long id) {
        ClientType clientType = clientTypeService.getById(id);
        ClientTypeDtoFull clientTypeDtoFull = modelMapper.map(clientType, ClientTypeDtoFull.class);
        return new ResponseEntity<>(clientTypeDtoFull, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientTypeDtoFull> updateProduct(@PathVariable("id") Long id, @RequestBody ClientTypeDtoBase clientTypeDtoBase) {
        if (!Objects.equals(id, clientTypeDtoBase.getId())){
            // if different
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ClientType updatedClientType = clientTypeService.update(clientTypeDtoBase);
        ClientTypeDtoFull clientTypeDtoFull = modelMapper.map(updatedClientType, ClientTypeDtoFull.class);
        return new ResponseEntity<>(clientTypeDtoFull, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        if (clientTypeService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
