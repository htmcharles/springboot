package rw.rca.hotelbookingsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rw.rca.hotelbookingsystem.models.Staff;
import rw.rca.hotelbookingsystem.repositories.StaffRepository;

@SpringBootApplication
public class HotelbookingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelbookingsystemApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StaffRepository srepo) {
		return args -> {
			Staff staff = new Staff("mugisha@gmail.com","Mike","Mugisha");
			srepo.save(staff);
		};
	}
}
