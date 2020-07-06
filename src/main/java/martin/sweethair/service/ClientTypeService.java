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

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ClientTypeService implements ControllerService<ClientTypeDtoBase> {

    private final ModelMapper modelMapper;

    private final ClientTypeRepository clientTypeRepository;

    @Transactional
    @Override
    public ClientTypeDtoBase save(ClientTypeDtoBase dtoBase) {
        ClientType saved = clientTypeRepository.save(modelMapper.map(dtoBase, ClientType.class));
        dtoBase.setId(saved.getId());
        return dtoBase;
    }

    @Transactional(readOnly = true)
    @Override
    public Set<ClientTypeDtoBase> getAll() {
        return clientTypeRepository.findAll()
                .stream()
                .map(clientType -> modelMapper.map(clientType, ClientTypeDtoBase.class))
                .collect(Collectors.toSet());
    }

    @Override
    public ClientTypeDtoBase getById(Long id) {
        ClientType clientType = this.getEntityById(id);
        return modelMapper.map(clientType, ClientTypeDtoBase.class);
    }

    public ClientType getEntityById(Long id) {
        return clientTypeRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No client type found with ID -> " + id));
    }

    public ClientTypeDtoBase getByName(String name) {
        ClientType clientType = clientTypeRepository.findFirstByName(name);
        if (clientType == null) {
            throw new SpringDataException("No client type found with name -> " + name);
        }
        return modelMapper.map(clientType, ClientTypeDtoBase.class);
    }

    @Override
    public ClientTypeDtoBase update(ClientTypeDtoBase dtoBase) {
        ClientType clientType = this.getEntityById(dtoBase.getId());

        if (clientType.getId().equals(dtoBase.getId())) {
            if (!clientType.equals(modelMapper.map(dtoBase, ClientType.class))) {
                clientType.setName(dtoBase.getName());
                clientTypeRepository.save(clientType);
            }
        }
        return modelMapper.map(clientType, ClientTypeDtoBase.class);
    }

    @Override
    public boolean deleteById(Long id) {
        ClientType clientType = this.getEntityById(id);

        if (clientType.getClients().isEmpty()) {
            clientTypeRepository.delete(clientType);
            return true;
        }
        return false;
    }
}
