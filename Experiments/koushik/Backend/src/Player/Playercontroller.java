import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
import src.Player;

public class PlayerController {

    final
    PlayerRepository PlayerRepository;

    private final String success = "Logged in successfully";

    public PlayerController(PlayerRepository PlayerRepository) {
        this.PlayerRepository = PlayerRepository;
    }

    @GetMapping(path = "/Player")
    List<Player> getAllPlayers(){
        return PlayerRepository.findAll();
    }

    @GetMapping(path = "/Player/{id}")
    Player getPlayerById(@PathVariable int id){
        return PlayerRepository.findById(id);
    }

    @PostMapping(path = "/Player")
    String createPlayer(@RequestBody Player Player){
        String failure = "Wrong Password or UserName";
        if (Player == null)
            return failure;
        PlayerRepository.save(Player);
        return success;
    }

    @PutMapping(path = "/Player/{id}")
    Player updatePlayer(@PathVariable int id, @RequestBody Player request){
        Player Player = PlayerRepository.findById(id);
        if(Player == null)
            return null;
        PlayerRepository.save(request);
        return PlayerRepository.findById(id);
    }

    @DeleteMapping(path = "/Player/{id}")
    String deletePlayer(@PathVariable int id){
        PlayerRepository.deleteById(id);
        return success;
    }
}
