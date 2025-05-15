package rw.rca.hatumaems.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.rca.hatumaems.models.AuditLog;
import rw.rca.hatumaems.models.Equipment;
import rw.rca.hatumaems.models.User;
import rw.rca.hatumaems.repositories.AuditLogRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    public AuditLog logAllocation(Equipment equipment, User user, String action, String details) {
        AuditLog log = new AuditLog();
        log.setEquipment(equipment);
        log.setUser(user);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        log.setDetails(details);
        return auditLogRepository.save(log);
    }

    public List<AuditLog> getEquipmentHistory(Long equipmentId) {
        return auditLogRepository.findByEquipmentIdOrderByTimestampDesc(equipmentId);
    }

    public List<AuditLog> getUserHistory(Long userId) {
        return auditLogRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAllByOrderByTimestampDesc();
    }
}
