package rw.rca.hotelbookingsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.rca.hotelbookingsystem.models.Staff;
import rw.rca.hotelbookingsystem.repositories.StaffRepository;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepo;
    public void addStaff(Staff staff) {
        staffRepo.save(staff);
    }
    public List<Staff> findAllStaffs() {
        return staffRepo.findAll();
    }
    public void deleteStaff(Staff staff) {
        staffRepo.delete(staff);
    }

    public Staff findByName(String name) {
        return  staffRepo.findByFirstName(name);
    }
}
