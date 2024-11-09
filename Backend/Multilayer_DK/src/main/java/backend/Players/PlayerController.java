package backend.Players;

import backend.Achievements.Achievements;
import backend.Achievements.AchievementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController{

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    AchievementsRepository achievementsRepository;

    private final String success = "{\"message\":\"success\"}";
    public final String failure = "{\"message\":\"failure\"}";

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping(path = "/Player")
    List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    @GetMapping(path = "/Player/{id}")
    Optional<Player> getPlayerById( @PathVariable Long id){
        return playerRepository.findById(id);
    }

    @PostMapping(path = "/Player")
    String createPlayer(@RequestBody Player player){
        if (player == null)
            return failure;
        playerRepository.save(player);
        return success;
    }

    @PutMapping("/Player/{id}")
    Optional<Player> updatePlayer(@PathVariable Long id, @RequestBody Player request){
        Optional<Player> player = playerRepository.findById(id);
        playerRepository.save(request);
        return playerRepository.findById(id);
    }

    @DeleteMapping(path = "/Player/{id}")
    String deletePlayer(@PathVariable Long id){
        playerRepository.deleteById(id);
        return success;
    }

    //start of relationship methods
    @PostMapping("/{playerId}/achievements/{achievementId}")
    public ResponseEntity<String> addAchievementToPlayer(
            @PathVariable Long playerId,
            @PathVariable Long achievementId) {

        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        Optional<Achievements> optionalAchievement = achievementsRepository.findById(achievementId);

        if (optionalPlayer.isEmpty() || optionalAchievement.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Player player = optionalPlayer.get();
        Achievements achievement = optionalAchievement.get();

        player.addAchievement(achievement);
        playerRepository.save(player);

        return ResponseEntity.ok("{\"message\":\"Achievement added to player\"}");
    }
}
