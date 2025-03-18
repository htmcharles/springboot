package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hotelbookingsystem.models.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
