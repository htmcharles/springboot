package rw.rca.hotelbookingsystem.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rw.rca.hotelbookingsystem.models.Address;
import rw.rca.hotelbookingsystem.models.User;
import rw.rca.hotelbookingsystem.models.UserRole;
import rw.rca.hotelbookingsystem.services.UserService;

import java.util.Scanner;

@Component
public class UserCreatorRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0 && args[0].equals("create-user")) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Creating a new user...");

            // Get user details
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            System.out.print("Phone Number: ");
            String phoneNumber = scanner.nextLine();

            // Get address details
            System.out.println("\nAddress Details:");
            System.out.print("District: ");
            String district = scanner.nextLine();

            System.out.print("Sector: ");
            String sector = scanner.nextLine();

            System.out.print("Street Number: ");
            String streetNo = scanner.nextLine();

            // Get role
            System.out.println("\nAvailable Roles:");
            for (UserRole role : UserRole.values()) {
                System.out.println(role.name());
            }
            System.out.print("Role (default: CUSTOMER): ");
            String roleInput = scanner.nextLine().toUpperCase();
            UserRole role = roleInput.isEmpty() ? UserRole.CUSTOMER : UserRole.valueOf(roleInput);

            // Create address
            Address address = new Address();
            address.setDistrict(district);
            address.setSector(sector);
            address.setStreetNO(streetNo);

            // Create user
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhoneNumber(phoneNumber);
            user.setAddress(address);
            user.setRole(role);

            try {
                User createdUser = userService.registerUser(user);
                System.out.println("\nUser created successfully!");
                System.out.println("User ID: " + createdUser.getId());
                System.out.println("Email: " + createdUser.getEmail());
                System.out.println("Role: " + createdUser.getRole());
            } catch (Exception e) {
                System.err.println("Error creating user: " + e.getMessage());
            }

            scanner.close();
        }
    }
}
