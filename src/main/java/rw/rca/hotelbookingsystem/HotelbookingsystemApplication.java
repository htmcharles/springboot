package rw.rca.hotelbookingsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rw.rca.hotelbookingsystem.models.*;
import rw.rca.hotelbookingsystem.repositories.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class HotelbookingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelbookingsystemApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StaffRepository srepo, GuestRepository grepo,
									 RoomRepository rrepo, BookingRepository brepo,
									 PaymentRepository prepo) {
		return args -> {
			// Only add test data if the repositories are empty
			if (srepo.count() == 0 && grepo.count() == 0 && rrepo.count() == 0) {
				// Create Staff members
				Staff staff1 = new Staff("john.doe@hotel.com", "Doe", "John");
				Staff staff2 = new Staff("jane.smith@hotel.com", "Smith", "Jane");
				Staff staff3 = new Staff("bob.johnson@hotel.com", "Johnson", "Bob");
				Staff staff4 = new Staff("mary.williams@hotel.com", "Williams", "Mary");

				// Create a list of staff members
				List<Staff> staffList = new ArrayList<>();
				staffList.add(staff1);
				staffList.add(staff2);
				staffList.add(staff3);
				staffList.add(staff4);

				// Save all staff members at once
				srepo.saveAll(staffList);

				// Create Rooms
				Room room1 = new Room("101", "Standard", 100.0, "Available");
				Room room2 = new Room("102", "Deluxe", 200.0, "Available");
				Room room3 = new Room("201", "Suite", 300.0, "Available");
				rrepo.save(room1);
				rrepo.save(room2);
				rrepo.save(room3);

				// Create Guests with auto-generated IDs
				Address address1 = new Address("Nyabihu", "Mukamira", "23 Main St");
				Address address2 = new Address("Nyabihu", "Mukamira", "23 Main St");

				Guest guest1 = new Guest(null, "Alice Johnson", "+250788123456", "alice@email.com", address1);
				Guest guest2 = new Guest(null, "Bob Wilson", "+250789123456", "bob@email.com", address2);
				grepo.save(guest1);
				grepo.save(guest2);

				// Create ManyToMany relationship between Staff and Room
				staff1.assignRoom(room1);
				staff1.assignRoom(room2);
				staff2.assignRoom(room2);
				staff2.assignRoom(room3);
				staff3.assignRoom(room1);
				staff3.assignRoom(room3);
				staff4.assignRoom(room2);

				// Update all staff members with their room assignments
				srepo.saveAll(staffList);

				// Create Booking using the bidirectional relationship helper methods
				Date checkIn = new Date();
				Date checkOut = new Date(checkIn.getTime() + (7 * 24 * 60 * 60 * 1000L)); // 7 days later

				Booking booking1 = new Booking(null, room1, guest1, checkIn, checkOut);
				Booking booking2 = new Booking(null, room2, guest2, checkIn, checkOut);

				// Use helper methods to establish bidirectional relationships
				guest1.addBooking(booking1);
				guest2.addBooking(booking2);

				// Save the bookings
				brepo.save(booking1);
				brepo.save(booking2);

				// Create Payments with OneToOne relationship
				Payment payment1 = new Payment(null, booking1, 700.0, "Completed");
				Payment payment2 = new Payment(null, booking2, 1400.0, "Completed");

				// Establish bidirectional relationship
				booking1.setPayment(payment1);
				booking2.setPayment(payment2);

				// Save the payments
				prepo.save(payment1);
				prepo.save(payment2);

				// Print confirmation
				System.out.println("Test data created successfully:");
				System.out.println("Staff members: " + srepo.count());
				System.out.println("Rooms: " + rrepo.count());
				System.out.println("Guests: " + grepo.count());
				System.out.println("Bookings: " + brepo.count());
				System.out.println("Payments: " + prepo.count());

				// Print relationship information
				System.out.println("\nRelationship Information:");
				System.out.println("Staff " + staff1.getFirstName() + " is assigned to " + staff1.getAssignedRooms().size() + " rooms");
				System.out.println("Guest " + guest1.getName() + " has " + guest1.getBookings().size() + " bookings");
				System.out.println("Room " + room1.getRoomNumber() + " has " + room1.getBookings().size() + " bookings and "
								 + room1.getAssignedStaff().size() + " staff assigned");
				System.out.println("Booking ID " + booking1.getBookingID() + " has payment: " + (booking1.getPayment() != null));
			} else {
				System.out.println("Test data already exists, skipping initialization");
			}
		};
	}
}
