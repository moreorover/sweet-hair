package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.ClientDtoBase;
import martin.sweethair.repository.ClientTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class ClientService implements ControllerService<ClientDtoBase> {

    private final ModelMapper modelMapper;

    private final ClientTypeRepository clientTypeRepository;

    @Override
    public ClientDtoBase save(ClientDtoBase dtoBase) {
        return null;
    }

    @Override
    public Set<ClientDtoBase> getAll() {
        return null;
    }

    @Override
    public ClientDtoBase getById(Long id) {
        return null;
    }

    @Override
    public ClientDtoBase update(ClientDtoBase dtoBase) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
