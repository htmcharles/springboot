package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hotelbookingsystem.models.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
}
