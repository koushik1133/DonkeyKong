package backend.Dk;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import backend.Dk.*;

@RestController
public class DkController{

    final
    DkRepository dkRepository;

    private final String success = "{\"message\":\"success\"}";
    public final String failure = "{\"message\":\"failure\"}";

    public DkController(DkRepository dkRepository) {
        this.dkRepository = dkRepository;
    }

    @GetMapping(path = "/Dk")
    List<Dk_object> getAllUsers(){
        return dkRepository.findAll();
    }

    @GetMapping(path = "/Dk/{id}")
    Dk_object getUserById( @PathVariable int id){
        return dkRepository.findById(id);
    }

    @PostMapping(path = "/Dk")
    String createUser(@RequestBody Dk_object player){
        if (player == null)
            return failure;
        dkRepository.save(player);
        return success;
    }

    /*@PutMapping("/Dk/{id}")
    Dk_object updateUser(@PathVariable int id, @RequestBody Dk_object request){
        Dk_object player = dkRepository.findById(id);
        if(player == null)
            return null;
        playerRepository.save(request);
        return playerRepository.findById(id);
    }*/

    @DeleteMapping(path = "/Dk/{id}")
    String deletePlayer(@PathVariable int id){
        dkRepository.deleteById(id);
        return success;
    }
}
