package rw.rca.hatumaems.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hatumaems.models.AuditLog;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByEquipmentIdOrderByTimestampDesc(Long equipmentId);
    List<AuditLog> findByUserIdOrderByTimestampDesc(Long userId);
    List<AuditLog> findAllByOrderByTimestampDesc();
}
