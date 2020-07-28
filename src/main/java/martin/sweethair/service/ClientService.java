package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.PostClientDto;
import martin.sweethair.dto.full.ClientDtoFull;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.Client;
import martin.sweethair.model.ClientType;
import martin.sweethair.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class ClientService {

    private final ModelMapper modelMapper;

    private final ClientRepository clientRepository;
    private final ClientTypeService clientTypeService;

    @Transactional
    public Client save(PostClientDto postClientDto) {
        ClientType clientType = clientTypeService.getByName(postClientDto.getClientType().getName());
        Client newClient = Client.builder()
                .name(postClientDto.getName())
                .clientType(clientType)
                .build();
        return clientRepository.save(newClient);
    }

    @Transactional(readOnly = true)
    public Set<Client> getAll() {
        return new HashSet<>(clientRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No client found with ID -> " + id));
    }

    @Transactional(readOnly = true)
    public Client getByName(String name) {
        return clientRepository.findFirstByName(name);
    }

    @Transactional
    public Client update(ClientDtoFull dtoFull) {
        ClientType clientType = this.clientTypeService.getByName(dtoFull.getClientType().getName());
        Client client = this.getById(dtoFull.getId());

        if (client.getId().equals(dtoFull.getId())) {
            if (!client.equals(modelMapper.map(dtoFull, Client.class))) {
                client.setName(dtoFull.getName());
                client.setClientType(clientType);
                clientRepository.save(client);
            }
        }
        return client;
    }

    @Transactional
    public boolean deleteById(Long id) {
        Client client = this.getById(id);

        if (client.getOperations().isEmpty()) {
            clientRepository.delete(client);
            return true;
        }
        return false;
    }
}
