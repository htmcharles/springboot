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

				// Create Guests with auto-generated IDs
				Address address1 = new Address("Nyabihu", "Mukamira", "23 Main St");
				Address address2 = new Address("Nyabihu", "Mukamira", "23 Main St");

				Guest guest1 = new Guest(null, "Alice Johnson", "+250788123456", "alice@email.com", address1);
				Guest guest2 = new Guest(null, "Bob Wilson", "+250789123456", "bob@email.com", address2);
				List<Guest> guests = new ArrayList<>();
				guests.add(guest1);
				guests.add(guest2);
				grepo.saveAll(guests);

				// Now retrieve the saved guests to get their generated IDs
				List<Guest> savedGuests = grepo.findAll();
				Guest savedGuest1 = savedGuests.get(0);
				Guest savedGuest2 = savedGuests.get(1);

				// Create Bookings with auto-generated IDs
				Date checkIn = new Date();
				Date checkOut = new Date(checkIn.getTime() + (7 * 24 * 60 * 60 * 1000L)); // 7 days later

				Booking booking1 = new Booking(null, room1, savedGuest1, checkIn, checkOut);
				Booking booking2 = new Booking(null, room2, savedGuest2, checkIn, checkOut);
				List<Booking> bookings = new ArrayList<>();
				bookings.add(booking1);
				bookings.add(booking2);
				brepo.saveAll(bookings);

				// Now retrieve the saved bookings to get their generated IDs
				List<Booking> savedBookings = brepo.findAll();
				Booking savedBooking1 = savedBookings.get(0);
				Booking savedBooking2 = savedBookings.get(1);

				// Create Payments with auto-generated IDs
				Payment payment1 = new Payment(null, savedBooking1, 700.0, "Completed");
				Payment payment2 = new Payment(null, savedBooking2, 1400.0, "Completed");
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
