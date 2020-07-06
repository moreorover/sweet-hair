package martin.sweethair.service;

import martin.sweethair.dto.base.DtoBase;

import java.util.Set;

public interface ControllerService<T extends DtoBase> {

    T save(T dtoBase);
    Set<T> getAll();
    T getById(Long id);
    T update(T dtoBase);
    boolean deleteById(Long id);
}
