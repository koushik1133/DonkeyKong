package Dk.Players.Player;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class PlayerController {

    final
    playerRepository playerRepository;

    //final
    //LaptopRepository laptopRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    public PlayerController(playerRepository playerRepository) {
        this.playerRepository = playerRepository;
        //this.laptopRepository = laptopRepository;
    }

    @GetMapping(path = "/Player")
    List<Players> getAllUsers(){
        return playerRepository.findAll();
    }

    @GetMapping(path = "/Player/{id}")
    Player getUserById( @PathVariable int id){
        return playerRepository.findById(id);
    }

    @PostMapping(path = "/Player")
    String createUser(@RequestBody Player player){
        if (player == null)
            return failure;
        playerRepository.save(player);
        return success;
    }

    @PutMapping("/Player/{id}")
    Player updateUser(@PathVariable int id, @RequestBody Player request){
        Player player = playerRepository.findById(id);
        if(player == null)
            return null;
        playerRepository.save(request);
        return playerRepository.findById(id);
    }

    @PutMapping("/Player/{PlayerId}/laptops/{laptopId}")
    String assignLaptopToUser(@PathVariable int userId,@PathVariable int laptopId){
        Player player = playerRepository.findById(userId);
        if(player == null)
            return failure;
        player.setUser(player);
        playerRepository.save(player);
        return success;
    }

    @DeleteMapping(path = "/Player/{id}")
    String deletePlayer(@PathVariable int id){
        playerRepository.deleteById(id);
        return success;
    }
}
