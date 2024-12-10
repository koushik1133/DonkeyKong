package backend.Players;

import backend.Achievements.Achievements;
import backend.Achievements.AchievementsRepository;
import backend.Dk.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/player") //TODO: why was this changed @Koushik
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

    /*@GetMapping(path = "/Player/{id}")
    Optional<Player> getPlayerById( @PathVariable Long id){
        return playerRepository.findById(id);
    }*/

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{playerId}/score")
    public int getPlayerScore(@PathVariable Long playerId) {
        Player player = playerService.getPlayerById(playerId);
        /*
        TODO: What is going on here
            - player.getScore() returns a Score object (containing int score, int id)
            - player.getScore().getScore() specifically returns the int score
            - player.getScore().getScoreValue() also returns int score
            - not even sure what the point of (int value) is....
         */
        return player.getScore().getScoreValue(); // Return the current score
    }

    @PostMapping(path = "/Player")
    String createPlayer(@RequestBody Player player){
        if (player == null)
            return failure;
        playerRepository.save(player);
        return success;
    }

    @PutMapping("/Player/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player request) {
        Player player = playerRepository.findById(id)
                .orElseThrow();

        // Update the fields
        if (request.getUsername() != null) player.setUsername(request.getUsername());
        if (request.getPassword() != null) player.setPassword(request.getPassword());
        if (request.getSprite() != null) player.setSprite(request.getSprite());

        playerRepository.save(player);
        return ResponseEntity.ok(player);
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

        //TODO: go over this a little bit
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
