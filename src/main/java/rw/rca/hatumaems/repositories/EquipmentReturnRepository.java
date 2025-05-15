package rw.rca.hatumaems.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hatumaems.models.EquipmentReturn;
import rw.rca.hatumaems.models.EquipmentRequest;
import java.util.Optional;

public interface EquipmentReturnRepository extends JpaRepository<EquipmentReturn, Long> {
    Optional<EquipmentReturn> findByRequest(EquipmentRequest request);
    Optional<EquipmentReturn> findByRequest_Id(Long requestId);
}
