package backend.Scores;

import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: Create Scores




@RestController
public class ScoreController{

    final
    ScoreRepository ScoreRepository;

    private final String success = "{\"message\":\"success\"}";
    public final String failure = "{\"message\":\"failure\"}";

    public ScoreController(ScoreRepository ScoreRepository) {
        this.ScoreRepository = ScoreRepository;
    }

    @GetMapping(path = "/Score")
    List<Score> getAllUsers(){
        return ScoreRepository.findAll();
    }

    @GetMapping(path = "/Score/{id}")
    Score getUserById( @PathVariable int id){
        return ScoreRepository.findById(id);
    }

    @PostMapping(path = "/Score")
    String createUser(@RequestBody Score score){
        if (score == null)
            return failure;
        ScoreRepository.save(score);
        return success;
    }

    @PutMapping("/Score/{id}")
    Score updateUser(@PathVariable int id, @RequestBody Score request){
        Score score = ScoreRepository.findById(id);
        if(score == null)
            return null;
        ScoreRepository.save(request);
        return ScoreRepository.findById(id);
    }

    @DeleteMapping(path = "/Score/{id}")
    String deleteScore(@PathVariable int id){
        ScoreRepository.deleteById(id);
        return success;
    }
}
