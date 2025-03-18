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
				List<Staff> staffs = new ArrayList<>();
				staffs.add(staff1);
				staffs.add(staff2);
				srepo.saveAll(staffs);

				// Create Rooms
				Room room1 = new Room("101", "Standard", 100.0, "Available");
				Room room2 = new Room("102", "Deluxe", 200.0, "Available");
				Room room3 = new Room("201", "Suite", 300.0, "Available");
				List<Room> rooms = new ArrayList<>();
				rooms.add(room1);
				rooms.add(room2);
				rooms.add(room3);
				rrepo.saveAll(rooms);

				// Create Guests
				Address address1 = new Address("123 Main St", "Kigali", "Rwanda");
				Address address2 = new Address("456 Park Ave", "Kigali", "Rwanda");

				Guest guest1 = new Guest(1, "Alice Johnson", "+250788123456", "alice@email.com", address1);
				Guest guest2 = new Guest(2, "Bob Wilson", "+250789123456", "bob@email.com", address2);
				List<Guest> guests = new ArrayList<>();
				guests.add(guest1);
				guests.add(guest2);
				grepo.saveAll(guests);

				// Create Bookings
				Date checkIn = new Date();
				Date checkOut = new Date(checkIn.getTime() + (7 * 24 * 60 * 60 * 1000L)); // 7 days later

				Booking booking1 = new Booking(1, room1, guest1, checkIn, checkOut);
				Booking booking2 = new Booking(2, room2, guest2, checkIn, checkOut);
				List<Booking> bookings = new ArrayList<>();
				bookings.add(booking1);
				bookings.add(booking2);
				brepo.saveAll(bookings);

				// Create Payments
				Payment payment1 = new Payment(1, booking1, 700.0, "Completed");
				Payment payment2 = new Payment(2, booking2, 1400.0, "Completed");
				List<Payment> payments = new ArrayList<>();
				payments.add(payment1);
				payments.add(payment2);
				prepo.saveAll(payments);

				// Print confirmation
				System.out.println("Test data created successfully:");
				System.out.println("Staff members: " + srepo.count());
				System.out.println("Rooms: " + rrepo.count());
				System.out.println("Guests: " + grepo.count());
				System.out.println("Bookings: " + brepo.count());
				System.out.println("Payments: " + prepo.count());
			} else {
				System.out.println("Test data already exists, skipping initialization");
			}
		};
	}
}
