package backend.Administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    public AdminController(AdminRepository adminRepository) {
//        this.adminRepository = adminRepository;
//    }

    @GetMapping(path = "/Admin")
    List<Admin> getAllUsers(){
        return adminRepository.findAll();
    }

    @GetMapping(path = "/Admin/{id}")
    Admin getUserById( @PathVariable int id){
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
    Admin updateUser(@PathVariable int id, @RequestBody Admin request){
        Admin admin = adminRepository.findById(id);
        if(admin == null)
            return null;
        adminRepository.save(request);
        return adminRepository.findById(id);
    }

    @DeleteMapping(path = "/Admin/{id}")
    String deleteAdmin(@PathVariable int id){
        adminRepository.deleteById(id);
        return success;
    }
}
