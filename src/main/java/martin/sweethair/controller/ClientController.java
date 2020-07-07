package martin.sweethair.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.PostClientDto;
import martin.sweethair.dto.base.ClientDtoBase;
import martin.sweethair.dto.base.ClientTypeDtoBase;
import martin.sweethair.dto.full.ClientDtoFull;
import martin.sweethair.model.Client;
import martin.sweethair.model.ClientType;
import martin.sweethair.service.ClientService;
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
@RequestMapping("/api/client")
@AllArgsConstructor
@Slf4j
public class ClientController {

    private final ModelMapper modelMapper;

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDtoFull> create(@RequestBody PostClientDto postClientDto) {
        Client saved = clientService.save(postClientDto);
        return new ResponseEntity<>(modelMapper.map(saved, ClientDtoFull.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<ClientDtoFull>> getAll() {
        Set<Client> clients = clientService.getAll();
        Set<ClientDtoFull> clientDtoFulls = clients.stream()
                .map(client -> modelMapper.map(client, ClientDtoFull.class))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(clientDtoFulls, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDtoFull> getById(@PathVariable("id") Long id) {
        Client client = clientService.getById(id);
        ClientDtoFull clientDtoBase = modelMapper.map(client, ClientDtoFull.class);
        return new ResponseEntity<>(clientDtoBase, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDtoFull> update(@PathVariable("id") Long id, @RequestBody ClientDtoFull clientDtoFull) {
        if (!Objects.equals(id, clientDtoFull.getId())){
            // if different
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Client updatedClient = clientService.update(clientDtoFull);
        ClientDtoFull clientDtoFullUpdated = modelMapper.map(updatedClient, ClientDtoFull.class);
        return new ResponseEntity<>(clientDtoFullUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (clientService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
