package backend.Players;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import backend.Players.*;

@RestController
public class PlayerController{

    final
    PlayerRepository playerRepository;

    private final String success = "{\"message\":\"success\"}";
    public final String failure = "{\"message\":\"failure\"}";

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping(path = "/Player")
    List<Player> getAllUsers(){
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

    @DeleteMapping(path = "/Player/{id}")
    String deletePlayer(@PathVariable int id){
        playerRepository.deleteById(id);
        return success;
    }
}
