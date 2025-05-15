/**
 * Main application class for the HATUMA Equipment Management System.
 * This class serves as the entry point for the Spring Boot application and contains
 * configuration for CORS and initial data setup.
 */
package rw.rca.hatumaems;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rw.rca.hatumaems.models.*;
import rw.rca.hatumaems.repositories.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class HatumaEmsApplication {

    /**
     * Main method that starts the Spring Boot application.
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(HatumaEmsApplication.class, args);
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) settings for the application.
     * Allows requests from any origin for development purposes.
     * @return WebMvcConfigurer with CORS configuration
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }

    /**
     * Initializes sample data for the application if the database is empty.
     * Creates test users (admin and staff) and equipment items.
     *
     * @param userRepo Repository for managing users
     * @param equipmentRepo Repository for managing equipment
     * @param requestRepo Repository for managing equipment requests
     * @param returnRepo Repository for managing equipment returns
     * @param auditLogRepo Repository for managing audit logs
     * @return CommandLineRunner that executes the initialization logic
     */
    @Bean
    CommandLineRunner initData(
            UserRepository userRepo,
            EquipmentRepository equipmentRepo,
            EquipmentRequestRepository requestRepo,
            EquipmentReturnRepository returnRepo,
            AuditLogRepository auditLogRepo) {
        return args -> {
            // Only add test data if the repositories are empty
            if (userRepo.count() == 0) {
                // Create Admin Users
                User admin1 = new User();
                admin1.setEmail("admin@hatuma.com");
                admin1.setPassword("admin123");
                admin1.setFirstName("Admin");
                admin1.setLastName("User");
                admin1.setFullName("Admin User");
                admin1.setDepartment("Administration");
                admin1.setPhoneNumber("0788123456");
                admin1.setUserType("ADMIN");
                admin1.setActive(true);
                userRepo.save(admin1);

                User admin2 = new User();
                admin2.setEmail("admin2@hatuma.com");
                admin2.setPassword("admin123");
                admin2.setFirstName("Sarah");
                admin2.setLastName("Manager");
                admin2.setFullName("Sarah Manager");
                admin2.setDepartment("Equipment Management");
                admin2.setPhoneNumber("0788123457");
                admin2.setUserType("ADMIN");
                admin2.setActive(true);
                userRepo.save(admin2);

                // Create Staff Users
                User staff1 = new User();
                staff1.setEmail("john.doe@hatuma.com");
                staff1.setPassword("staff123");
                staff1.setFirstName("John");
                staff1.setLastName("Doe");
                staff1.setFullName("John Doe");
                staff1.setDepartment("IT");
                staff1.setPhoneNumber("0789123456");
                staff1.setUserType("STAFF");
                staff1.setActive(true);
                userRepo.save(staff1);

                User staff2 = new User();
                staff2.setEmail("jane.smith@hatuma.com");
                staff2.setPassword("staff123");
                staff2.setFirstName("Jane");
                staff2.setLastName("Smith");
                staff2.setFullName("Jane Smith");
                staff2.setDepartment("Engineering");
                staff2.setPhoneNumber("0790123456");
                staff2.setUserType("STAFF");
                staff2.setActive(true);
                userRepo.save(staff2);

                User staff3 = new User();
                staff3.setEmail("bob.wilson@hatuma.com");
                staff3.setPassword("staff123");
                staff3.setFirstName("Bob");
                staff3.setLastName("Wilson");
                staff3.setFullName("Bob Wilson");
                staff3.setDepartment("Research");
                staff3.setPhoneNumber("0791123456");
                staff3.setUserType("STAFF");
                staff3.setActive(true);
                userRepo.save(staff3);

                // Create Equipment Items
                Equipment laptop = new Equipment();
                laptop.setName("Dell Latitude 5420");
                laptop.setType("Laptop");
                laptop.setDescription("Business laptop with 16GB RAM, 512GB SSD");
                laptop.setStatus("AVAILABLE");
                laptop.setQuantity(5);
                laptop.setLocation("IT Lab Room 201");
                equipmentRepo.save(laptop);

                Equipment projector = new Equipment();
                projector.setName("Epson EB-U42");
                projector.setType("Projector");
                projector.setDescription("Full HD Projector with wireless connectivity");
                projector.setStatus("AVAILABLE");
                projector.setQuantity(2);
                projector.setLocation("AV Equipment Room 101");
                equipmentRepo.save(projector);

                Equipment camera = new Equipment();
                camera.setName("Canon EOS 80D");
                camera.setType("Camera");
                camera.setDescription("Professional DSLR Camera with 18-135mm lens");
                camera.setStatus("AVAILABLE");
                camera.setQuantity(3);
                camera.setLocation("Media Room 301");
                equipmentRepo.save(camera);

                Equipment microscope = new Equipment();
                microscope.setName("Olympus BX53");
                microscope.setType("Laboratory Equipment");
                microscope.setDescription("Research-grade microscope with digital imaging");
                microscope.setStatus("MAINTENANCE");
                microscope.setQuantity(2);
                microscope.setLocation("Lab Room 401");
                equipmentRepo.save(microscope);

                // Create Equipment Requests

                // Case 1: Pending Request
                EquipmentRequest request1 = new EquipmentRequest();
                request1.setEquipment(laptop);
                request1.setRequester(staff1);
                request1.setPurpose("Software Development Project");
                request1.setDuration(24);
                request1.setStatus("PENDING");
                request1.setRequestDate(LocalDateTime.now());
                requestRepo.save(request1);

                // Case 2: Approved Request
                EquipmentRequest request2 = new EquipmentRequest();
                request2.setEquipment(projector);
                request2.setRequester(staff2);
                request2.setPurpose("Department Presentation");
                request2.setDuration(4);
                request2.setStatus("APPROVED");
                request2.setRequestDate(LocalDateTime.now().minusDays(1));
                request2.setApprovalDate(LocalDateTime.now().minusDays(1).plusHours(2));
                requestRepo.save(request2);

                // Case 3: Rejected Request
                EquipmentRequest request3 = new EquipmentRequest();
                request3.setEquipment(camera);
                request3.setRequester(staff3);
                request3.setPurpose("Photo Documentation");
                request3.setDuration(8);
                request3.setStatus("REJECTED");
                request3.setRequestDate(LocalDateTime.now().minusDays(2));
                request3.setApprovalDate(LocalDateTime.now().minusDays(2).plusHours(1));
                requestRepo.save(request3);

                // Case 4: Returned Request (Good Condition)
                EquipmentRequest request4 = new EquipmentRequest();
                request4.setEquipment(laptop);
                request4.setRequester(staff1);
                request4.setPurpose("Training Session");
                request4.setDuration(6);
                request4.setStatus("RETURNED");
                request4.setRequestDate(LocalDateTime.now().minusDays(3));
                request4.setApprovalDate(LocalDateTime.now().minusDays(3).plusHours(1));
                request4.setReturnDate(LocalDateTime.now().minusDays(2));
                requestRepo.save(request4);

                EquipmentReturn return1 = new EquipmentReturn();
                return1.setRequest(request4);
                return1.setCondition("GOOD");
                return1.setNotes("Equipment returned in perfect condition");
                returnRepo.save(return1);

                // Case 5: Returned Request (Damaged)
                EquipmentRequest request5 = new EquipmentRequest();
                request5.setEquipment(projector);
                request5.setRequester(staff2);
                request5.setPurpose("Client Meeting");
                request5.setDuration(2);
                request5.setStatus("RETURNED");
                request5.setRequestDate(LocalDateTime.now().minusDays(4));
                request5.setApprovalDate(LocalDateTime.now().minusDays(4).plusHours(1));
                request5.setReturnDate(LocalDateTime.now().minusDays(3));
                requestRepo.save(request5);

                EquipmentReturn return2 = new EquipmentReturn();
                return2.setRequest(request5);
                return2.setCondition("DAMAGED");
                return2.setNotes("Projector lamp needs replacement");
                returnRepo.save(return2);

                // Create Audit Logs
                AuditLog log1 = new AuditLog();
                log1.setEquipment(laptop);
                log1.setUser(staff1);
                log1.setAction("Equipment Requested");
                log1.setDetails("New request for laptop - Software Development Project");
                auditLogRepo.save(log1);

                AuditLog log2 = new AuditLog();
                log2.setEquipment(projector);
                log2.setUser(admin1);
                log2.setAction("Request Approved");
                log2.setDetails("Approved projector request for Department Presentation");
                auditLogRepo.save(log2);

                AuditLog log3 = new AuditLog();
                log3.setEquipment(camera);
                log3.setUser(admin2);
                log3.setAction("Request Rejected");
                log3.setDetails("Rejected camera request - Equipment unavailable");
                auditLogRepo.save(log3);

                System.out.println("Sample data has been created successfully!");
                System.out.println("\nDefault Admin Credentials:");
                System.out.println("Email: admin@hatuma.com");
                System.out.println("Password: admin123");
                System.out.println("\nDefault Staff Credentials:");
                System.out.println("Email: john.doe@hatuma.com");
                System.out.println("Password: staff123");
                System.out.println("\nAccess the API at: http://localhost:8098/api");
            } else {
                System.out.println("Database already contains data, skipping initialization.");
            }
        };
    }
}
