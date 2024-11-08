package backend.Administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author Sam Gumm
 * 
 */ 

@RestController
public class AdminController {

    @Autowired
    AdminRepository adminRepository;
    
    private final String success = "{\"message\":\"success\"}";
    public final String failure = "{\"message\":\"failure\"}";

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @GetMapping(path = "/Admin")
    List<Admin> getAllUsers(){
        return adminRepository.findAll();
    }

    @GetMapping(path = "/Admin/{id}")
    Optional<Admin> getUserById( @PathVariable Long id){
        return adminRepository.findById(id);
    }

    @PostMapping(path = "/Admin")
    String createUser(@RequestBody Admin admin){
        if (admin == null)
            return failure;
        adminRepository.save(admin);
        return success;
    }

    @PutMapping("/Admin/{id}")
    Optional<Admin> updateUser(@PathVariable Long id, @RequestBody Admin request){
        Optional<Admin> admin = adminRepository.findById(id);
        adminRepository.save(request);
        return adminRepository.findById(id);
    }

    @DeleteMapping(path = "/Admin/{id}")
    String deleteAdmin(@PathVariable Long id){
        adminRepository.deleteById(id);
        return success;
    }
}
