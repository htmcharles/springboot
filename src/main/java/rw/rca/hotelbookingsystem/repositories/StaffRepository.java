package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import rw.rca.hotelbookingsystem.controllers.StaffController;
import rw.rca.hotelbookingsystem.models.Address;
import rw.rca.hotelbookingsystem.models.PaymentStatus;
import rw.rca.hotelbookingsystem.models.Staff;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
    Staff findByCode(Integer code);
    Staff findByEmail(String email);
    Staff findByFirstName(String firstName);
    List<Staff> findByLastName(String lastName);
    List<Staff> findByFirstNameIgnoreCase(String firstName);
    List<Staff> findByLastNameIgnoreCase(String lastName);
    List<Staff> findByFirstNameContaining(String keyword);
    List<Staff> findByLastNameContaining(String keyword);
    List<Staff> findByFirstNameStartingWith(String prefix);
    List<Staff> findByLastNameStartingWith(String prefix);
    List<Staff> findByFirstNameEndingWith(String suffix);
    List<Staff> findByLastNameEndingWith(String suffix);
    //Find Using Multiple Fields
    Optional<Staff> findByFirstNameAndLastName(String firstName, String lastName);
    Optional<Staff> findByFirstNameAndEmail(String firstName, String email);
    List<Staff> findByLastNameAndEmail(String lastName, String email);
    List<Staff> findByFirstNameOrLastName(String firstName, String lastName);
    List<Staff> findByFirstNameOrEmail(String firstName, String email);
    List<Staff> findByLastNameOrEmail(String lastName, String email);
    //Find Using Like and IgnoreCase
    List<Staff> findByEmailLike(String emailPattern);
    List<Staff> findByFirstNameLikeIgnoreCase(String firstNamePattern);
    List<Staff> findByLastNameLikeIgnoreCase(String lastNamePattern);
    List<Staff> findByEmailContainingIgnoreCase(String emailKeyword);
    List<Staff> findByFirstNameContainingIgnoreCase(String firstNameKeyword);
    List<Staff> findByLastNameContainingIgnoreCase(String lastNameKeyword);
    List<Staff> findByEmailEndingWithIgnoreCase(String emailSuffix);
    List<Staff> findByEmailStartingWithIgnoreCase(String emailPrefix);
    //Sorting
    List<Staff> findAllByOrderByFirstNameAsc();
    List<Staff> findAllByOrderByLastNameAsc();
    List<Staff> findAllByOrderByEmailAsc();
    List<Staff> findAllByOrderByFirstNameDesc();
    List<Staff> findAllByOrderByLastNameDesc();
    List<Staff> findAllByOrderByEmailDesc();
    //Pagination
    Page<Staff> findAll(Pageable pageable);
    Page<Staff> findByFirstName(String firstName, Pageable pageable);
    Page<Staff> findByLastName(String lastName, Pageable pageable);
    Page<Staff> findByEmail(String email, Pageable pageable);
    Page<Staff> findByFirstNameContaining(String keyword, Pageable pageable);
    Page<Staff> findByLastNameContaining(String keyword, Pageable pageable);
    //Counting Methods
    Long countByFirstName(String firstName);
    Long countByLastName(String lastName);
    Long countByEmail(String email);
    Long countByFirstNameContaining(String keyword);
    Long countByLastNameContaining(String keyword);
    //Existence Check
    Boolean existsByEmail(String email);
    Boolean existsByFirstName(String firstName);
    Boolean existsByLastName(String lastName);
    Boolean existsByFirstNameAndLastName(String firstName, String lastName);
    //Custom Queries (JPQL and Native SQL)
    @Query("SELECT s FROM Staff s WHERE s.firstName = :firstName") List<Staff> findByFirstNameCustom(@Param("firstName") String firstName);
    @Query("SELECT s FROM Staff s WHERE s.lastName = :lastName") List<Staff> findByLastNameCustom(@Param("lastName") String lastName);
    @Query("SELECT s FROM Staff s WHERE s.email = :email") Optional<Staff> findByEmailCustom(@Param("email") String email);
    @Query("SELECT s FROM Staff s ORDER BY s.firstName ASC") List<Staff> findAllOrderByFirstName();
    @Query("SELECT s FROM Staff s ORDER BY s.lastName DESC") List<Staff> findAllOrderByLastNameDesc();
    @Query("SELECT s FROM Staff s WHERE LOWER(s.email) LIKE LOWER(CONCAT('%', :emailPart, '%'))") List<Staff> findByEmailLikeIgnoreCase(@Param("emailPart") String emailPart);
    @Query(value = "SELECT * FROM staff WHERE email = ?1", nativeQuery = true) Optional<Staff> findByEmailNative(String email);
    @Query(value = "SELECT * FROM staff ORDER BY first_name ASC", nativeQuery = true) List<Staff> findAllOrderByFirstNameNative();
    @Query(value = "SELECT COUNT(*) FROM staff WHERE last_name = ?1", nativeQuery = true) Long countByLastNameNative(String lastName);
    //Deleting Methods
    void deleteByEmail(String email);
    void deleteByFirstName(String firstName);
    void deleteByLastName(String lastName);
    void deleteByCode(Integer code);
    @Modifying
    @Query("DELETE FROM Staff s WHERE s.email = :email") void deleteByEmailCustom(@Param("email") String email);
    //Updating Methods
    @Modifying @Query("UPDATE Staff s SET s.firstName = :firstName WHERE s.email = :email") void updateFirstNameByEmail(@Param("firstName") String firstName, @Param("email") String email);
    @Modifying @Query("UPDATE Staff s SET s.lastName = :lastName WHERE s.email = :email") void updateLastNameByEmail(@Param("lastName") String lastName, @Param("email") String email);
    @Modifying @Query("UPDATE Staff s SET s.email = :newEmail WHERE s.email = :oldEmail") void updateEmail(@Param("newEmail") String newEmail, @Param("oldEmail") String oldEmail);
    //Finding Distinct Values
    List<Staff> findDistinctByFirstName(String firstName);
    List<Staff> findDistinctByLastName(String lastName);
    List<Staff> findDistinctByEmail(String email);
    List<Staff> findDistinctByFirstNameAndLastName(String firstName, String lastName);
    //Limiting Results
    List<Staff> findTop5ByOrderByFirstNameAsc();
    List<Staff> findTop3ByOrderByLastNameDesc();
    List<Staff> findFirstByOrderByEmailAsc();
    List<Staff> findFirst5ByFirstNameOrderByLastNameAsc(String firstName);
    //Boolean Queries
    List<Staff> findByFirstNameIsNotNull();
    List<Staff> findByLastNameIsNotNull();
    List<Staff> findByEmailIsNotNull();
    List<Staff> findByFirstNameIsNull();
    List<Staff> findByLastNameIsNull();
    List<Staff> findByEmailIsNull();
    //Case Insensitive Sorting
    List<Staff> findByFirstNameOrderByLastNameAsc(String firstName);
    List<Staff> findByLastNameOrderByFirstNameDesc(String lastName);
    //Bulk Operations
    @Transactional
    @Modifying @Query("DELETE FROM Staff s WHERE s.firstName = :firstName") void deleteByFirstNameBulk(@Param("firstName") String firstName);
    @Transactional @Modifying @Query("UPDATE Staff s SET s.lastName = :lastName WHERE s.code = :code") void updateLastNameByCode(@Param("lastName") String lastName, @Param("code") Integer code);

    interface PaymentRepository extends JpaRepository<Address.Payment, Long> {
        List<Address.Payment> findByBookingId(Long bookingId);
        List<Address.Payment> findByUserId(Long userId);
        List<Address.Payment> findByStatus(PaymentStatus status);
    }

    interface RoomRepository extends JpaRepository<StaffController.Room, Long> {
        List<StaffController.Room> findByType(String type);
        List<StaffController.Room> findByPricePerNightBetween(Double minPrice, Double maxPrice);
        List<StaffController.Room> findByCapacity(Integer capacity);
        List<StaffController.Room> findByIsAvailableTrue();
    }
}
