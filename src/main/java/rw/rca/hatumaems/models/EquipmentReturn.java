package rw.rca.hatumaems.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "equipment_returns")
public class EquipmentReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "request_id", nullable = false)
    private EquipmentRequest request;

    @Column(nullable = false)
    private LocalDateTime returnDate;

    @Column(nullable = false)
    private String condition; // GOOD, DAMAGED, NEEDS_MAINTENANCE

    private String notes;

    @PrePersist
    protected void onCreate() {
        returnDate = LocalDateTime.now();
    }
}
