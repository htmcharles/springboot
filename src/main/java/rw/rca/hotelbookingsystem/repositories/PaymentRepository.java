package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hotelbookingsystem.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
