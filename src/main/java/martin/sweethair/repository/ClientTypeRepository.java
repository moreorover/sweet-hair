package martin.sweethair.repository;

import martin.sweethair.model.ClientType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientTypeRepository extends JpaRepository<ClientType, Long> {
}
