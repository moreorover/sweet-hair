package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.ClientTypeDtoBase;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.ClientType;
import martin.sweethair.repository.ClientTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class ClientTypeService {

    private final ModelMapper modelMapper;

    private final ClientTypeRepository clientTypeRepository;

    @Transactional
    public ClientType save(ClientTypeDtoBase dtoBase) {
        ClientType newClientType = ClientType.builder()
                .name(dtoBase.getName())
                .build();

        return clientTypeRepository.save(newClientType);
    }

    @Transactional
    public Set<ClientType> getAll() {
        return new HashSet<>(clientTypeRepository.findAll());
    }

    public ClientType getById(Long id) {
        return clientTypeRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No client type found with ID -> " + id));
    }

    public ClientType getByName(String name) {
        ClientType clientType = clientTypeRepository.findFirstByName(name);
        if (clientType == null) {
            throw new SpringDataException("No client type found with name -> " + name);
        }
        return clientType;
    }

    public ClientType update(ClientTypeDtoBase dtoBase) {
        ClientType clientType = this.getById(dtoBase.getId());

        if (clientType.getId().equals(dtoBase.getId())) {
            if (!clientType.equals(modelMapper.map(dtoBase, ClientType.class))) {
                clientType.setName(dtoBase.getName());
                clientTypeRepository.save(clientType);
            }
        }
        return clientType;
    }

    public boolean deleteById(Long id) {
        ClientType clientType = this.getById(id);

        if (clientType.getClients().isEmpty()) {
            clientTypeRepository.delete(clientType);
            return true;
        }
        return false;
    }
}
