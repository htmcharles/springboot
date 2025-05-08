/**
 * Main application class for the Hotel Booking System.
 * This class serves as the entry point for the Spring Boot application and contains
 * configuration for CORS and initial data setup.
 */
package rw.rca.hotelbookingsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rw.rca.hotelbookingsystem.models.*;
import rw.rca.hotelbookingsystem.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class HotelbookingsystemApplication {

	/**
	 * Main method that starts the Spring Boot application.
	 * @param args Command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(HotelbookingsystemApplication.class, args);
	}

	/**
	 * Configures CORS (Cross-Origin Resource Sharing) settings for the application.
	 * Allows requests from the frontend development server (localhost:5173).
	 * @return WebMvcConfigurer with CORS configuration
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins("http://localhost:5173")
					.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
					.allowedHeaders("*")
					.allowCredentials(true);
			}
		};
	}

	/**
	 * Initializes sample data for the application if the database is empty.
	 * Creates test users, rooms, bookings, payments, and reviews.
	 *
	 * @param userRepo Repository for managing users
	 * @param roomRepo Repository for managing rooms
	 * @param bookingRepo Repository for managing bookings
	 * @param paymentRepo Repository for managing payments
	 * @param reviewRepo Repository for managing reviews
	 * @return CommandLineRunner that executes the initialization logic
	 */
	@Bean
	CommandLineRunner initData(UserRepository userRepo, RoomRepository roomRepo,
							 BookingRepository bookingRepo, PaymentRepository paymentRepo,
							 ReviewRepository reviewRepo) {
		return args -> {
			// Only add test data if the repositories are empty
			if (userRepo.count() == 0) {
				// Create Address objects first
				Address adminAddress = new Address();
				adminAddress.setDistrict("Kigali");
				adminAddress.setSector("Gasabo");
				adminAddress.setStreetNO("12A");

				Address customerAddress = new Address();
				customerAddress.setDistrict("Kigali");
				customerAddress.setSector("Gasabo");
				customerAddress.setStreetNO("45B");

				// Create Users with different roles
				User admin = new User();
				admin.setEmail("admin@hotel.com");
				admin.setPassword("admin123");
				admin.setFirstName("Admin");
				admin.setLastName("User");
				admin.setPhoneNumber("0788123456");
				admin.setAddress(adminAddress);
				admin.setRole(UserRole.ADMIN);
				userRepo.save(admin);

				User customer = new User();
				customer.setEmail("john.doe@example.com");
				customer.setPassword("securePassword123");
				customer.setFirstName("John");
				customer.setLastName("Doe");
				customer.setPhoneNumber("0789123456");
				customer.setAddress(customerAddress);
				customer.setRole(UserRole.CUSTOMER);
				userRepo.save(customer);

				// Create Rooms
				Room standardRoom = new Room();
				standardRoom.setRoomNumber("101");
				standardRoom.setType("Standard");
				standardRoom.setPrice(100.0);
				standardRoom.setStatus("AVAILABLE");
				standardRoom.setCapacity(2);
				roomRepo.save(standardRoom);

				Room deluxeRoom = new Room();
				deluxeRoom.setRoomNumber("201");
				deluxeRoom.setType("Deluxe");
				deluxeRoom.setPrice(200.0);
				deluxeRoom.setStatus("AVAILABLE");
				deluxeRoom.setCapacity(3);
				roomRepo.save(deluxeRoom);

				Room suiteRoom = new Room();
				suiteRoom.setRoomNumber("301");
				suiteRoom.setType("Suite");
				suiteRoom.setPrice(300.0);
				suiteRoom.setStatus("AVAILABLE");
				suiteRoom.setCapacity(4);
				roomRepo.save(suiteRoom);

				// Create a Booking
				Booking booking = new Booking();
				booking.setUser(customer);
				booking.setRoom(standardRoom);
				booking.setCheckInDate(LocalDate.now());
				LocalDate checkOutLocalDate = LocalDate.now().plusDays(2);
				Date checkOutDate = Date.from(checkOutLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
				booking.setCheckOut(checkOutDate);
				booking.setStatus("CONFIRMED");
				booking.setTotalPrice(200.0);
				bookingRepo.save(booking);

				// Create a Payment
				Payment payment = new Payment();
				payment.setBooking(booking);
				payment.setAmount(200.0);
				payment.setPaymentDate(LocalDateTime.now());
				payment.setPaymentMethod("CREDIT_CARD");
				payment.setStatus(PaymentStatus.COMPLETED);
				paymentRepo.save(payment);

				// Create Reviews
				Review review = new Review();
				review.setUser(customer);
				review.setRoom(standardRoom);
				review.setRating(5);
				review.setComment("Excellent stay! Very clean and comfortable room.");
				reviewRepo.save(review);

				System.out.println("Sample data has been created successfully!");
			} else {
				System.out.println("Database already contains data, skipping initialization.");
			}
		};
	}
}
