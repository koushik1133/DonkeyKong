package backend.Dk;

import backend.Players.Player;
import backend.Players.PlayerRepository;
import backend.Scores.Score;
import backend.Scores.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public Score saveScore(Score score) {
        return scoreRepository.save(score);
    }

    public Player getPlayerById(Long playerId) {
        return playerRepository.findById(playerId).orElse(null);
    }
}
