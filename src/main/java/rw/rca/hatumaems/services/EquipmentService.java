package rw.rca.hatumaems.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.rca.hatumaems.models.Equipment;
import rw.rca.hatumaems.repositories.EquipmentRepository;
import java.util.List;

/**
 * Service class for managing equipment-related operations.
 * Handles business logic for equipment creation, updates, and retrieval.
 *
 * @author HATUMA EMS Team
 * @version 1.0
 */
@Service
public class EquipmentService {
    /** Repository for equipment data access */
    @Autowired
    private EquipmentRepository equipmentRepository;

    /**
     * Creates a new equipment entry.
     * @param equipment the equipment object to create
     * @return the created equipment with generated ID
     */
    public Equipment createEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    /**
     * Updates an existing equipment entry.
     * @param id the ID of the equipment to update
     * @param equipment the updated equipment data
     * @return the updated equipment object
     * @throws RuntimeException if equipment with given ID is not found
     */
    public Equipment updateEquipment(Long id, Equipment equipment) {
        Equipment existingEquipment = getEquipmentById(id);
        existingEquipment.setName(equipment.getName());
        existingEquipment.setDescription(equipment.getDescription());
        existingEquipment.setStatus(equipment.getStatus());
        existingEquipment.setQuantity(equipment.getQuantity());
        return equipmentRepository.save(existingEquipment);
    }

    /**
     * Retrieves equipment by its ID.
     * @param id the ID of the equipment to retrieve
     * @return the equipment object
     * @throws RuntimeException if equipment with given ID is not found
     */
    public Equipment getEquipmentById(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
    }

    /**
     * Retrieves all equipment entries.
     * @return list of all equipment
     */
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    /**
     * Checks if equipment has sufficient quantity available.
     * @param equipmentId ID of the equipment
     * @param requestedQuantity quantity needed
     * @return true if sufficient quantity available, false otherwise
     */
    public boolean hasAvailableQuantity(Long equipmentId, int requestedQuantity) {
        Equipment equipment = getEquipmentById(equipmentId);
        return "AVAILABLE".equals(equipment.getStatus()) &&
               equipment.getQuantity() >= requestedQuantity;
    }

    /**
     * Decreases the quantity of available equipment when it is requested.
     * @param equipmentId ID of the equipment
     * @param quantity amount to decrease
     * @return updated equipment object
     * @throws RuntimeException if equipment not found or insufficient quantity
     */
    public Equipment decreaseQuantity(Long equipmentId, int quantity) {
        Equipment equipment = getEquipmentById(equipmentId);
        if (equipment.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient quantity available");
        }
        equipment.setQuantity(equipment.getQuantity() - quantity);
        if (equipment.getQuantity() == 0) {
            equipment.setStatus("OUT_OF_STOCK");
        }
        return equipmentRepository.save(equipment);
    }

    /**
     * Increases the quantity of available equipment when it is returned.
     * @param equipmentId ID of the equipment
     * @param quantity amount to increase
     * @param condition condition of returned equipment
     * @return updated equipment object
     */
    public Equipment increaseQuantity(Long equipmentId, int quantity, String condition) {
        Equipment equipment = getEquipmentById(equipmentId);
        equipment.setQuantity(equipment.getQuantity() + quantity);

        // Update status based on condition
        if ("DAMAGED".equals(condition) || "NEEDS_MAINTENANCE".equals(condition)) {
            equipment.setStatus("MAINTENANCE");
        } else if (equipment.getQuantity() > 0) {
            equipment.setStatus("AVAILABLE");
        }

        return equipmentRepository.save(equipment);
    }
}
