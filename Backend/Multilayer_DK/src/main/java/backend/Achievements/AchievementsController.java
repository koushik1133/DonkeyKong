package backend.Achievements;

import backend.Players.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class AchievementsController {

    @Autowired
    AchievementsRepository achievementsRepository;

    private final String success = "{\"message\":\"success\"}";
    public final String failure = "{\"message\":\"failure\"}";

    public AchievementsController(AchievementsRepository achievementsRepository) { this.achievementsRepository = achievementsRepository; }

    @GetMapping(path = "/Achievements")
    List<Achievements> getAllAchievements(){
        return achievementsRepository.findAll();
    }

    @GetMapping(path = "/Achievements/{id}")
    public Achievements getAchievementsById(@PathVariable Long id) {
        return achievementsRepository.findById(id)
                .orElseThrow();
    }

    @GetMapping("/{achievementId}/players")
    public ResponseEntity<Object> getPlayersByAchievement(@PathVariable Long achievementId) {
        Optional<Achievements> optionalAchievement = achievementsRepository.findById(achievementId);

        if (optionalAchievement.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Achievements achievement = optionalAchievement.get();
        Set<Player> players = achievement.getPlayers();

        return ResponseEntity.ok(players);
    }


    @PostMapping(path = "/Achievements")
    String createAchievements(@RequestBody Achievements achievement){
        if (achievement == null)
            return failure;
        achievementsRepository.save(achievement);
        return success;
    }

    @PutMapping("/Achievements/{id}")
    public Achievements updateAchievement(@PathVariable Long id, @RequestBody Achievements request) {
        return achievementsRepository.findById(id).map(achievement -> {
            achievement.setTitle(request.getTitle());
            achievement.setDescription(request.getDescription());
            return achievementsRepository.save(achievement);
        }).orElseThrow();
    }


    @DeleteMapping(path = "/Achievements/{id}")
    String deleteAchievements(@PathVariable Long id){
        achievementsRepository.deleteById(id);
        return success;
    }

}
