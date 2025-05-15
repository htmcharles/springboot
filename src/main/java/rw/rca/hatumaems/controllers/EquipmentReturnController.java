package rw.rca.hatumaems.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rca.hatumaems.models.EquipmentReturn;
import rw.rca.hatumaems.repositories.EquipmentReturnRepository;
import java.util.List;

@RestController
@RequestMapping("/api/returns")
@CrossOrigin
public class EquipmentReturnController {
    @Autowired
    private EquipmentReturnRepository returnRepository;

    @GetMapping
    public ResponseEntity<List<EquipmentReturn>> getAllReturns() {
        return ResponseEntity.ok(returnRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentReturn> getReturn(@PathVariable Long id) {
        return ResponseEntity.ok(returnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Return not found")));
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<EquipmentReturn> getReturnByRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(returnRepository.findByRequest_Id(requestId)
                .orElseThrow(() -> new RuntimeException("Return not found for request")));}}
