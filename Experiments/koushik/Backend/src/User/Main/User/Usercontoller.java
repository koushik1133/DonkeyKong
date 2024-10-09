package User;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
import src.Player;

public class Usercontroller {

    final
    UserRepository UserRepository;

    private final String success = "Logged in successfully";

    public UserController(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    @GetMapping(path = "/User")
    List<Player> getAllPlayers(){
        return UserRepository.findAll();
    }

    @GetMapping(path = "/User/{id}")
    User getUserById(@PathVariable int id){
        return UserRepository.findById(id);
    }

    @PostMapping(path = "/Player")
    String createUser(@RequestBody User User){
        String failure = "Wrong Password or UserName";
        if (User == null)
            return failure;
        UserRepository.save(User);
        return success;
    }

    @PutMapping(path = "/User/{id}")
    User updateUser(@PathVariable int id, @RequestBody User request){
        User  User = UserRepository.findById(id);
        if(User == null)
            return null;
        UserRepository.save(request);
        return UserRepository.findById(id);
    }

    @DeleteMapping(path = "/User/{id}")
    String deleteUser(@PathVariable int id){
        UserRepository.deleteById(id);
        return success;
    }
}
