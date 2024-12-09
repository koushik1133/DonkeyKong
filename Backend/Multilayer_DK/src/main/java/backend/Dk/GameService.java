package backend.Dk;

import backend.Players.Player;
import backend.Scores.Score;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private static final int BOMB_DAMAGE = 50;
    private static final int BARREL_DAMAGE = 100;
    private static final int BULLET_DAMAGE = 150;
    private static final int STAR_BONUS = 100;

    public Score checkCollisions(Player player, List<int[]> positions) {
        int playerScore = player.getScore() != null ? player.getScore().getScore() : 0;

        for (int[] pos : positions) {
            if (isBombCollision(pos)) {
                playerScore -= BOMB_DAMAGE;
            } else if (isBarrelCollision(pos)) {
                playerScore -= BARREL_DAMAGE;
            } else if (isBulletCollision(pos)) {
                playerScore -= BULLET_DAMAGE;
            } else if (isStarCollision(pos)) {
                playerScore += STAR_BONUS;
            }
        }

        player.getScore().setScore(playerScore);
        return player.getScore();
    }

    private boolean isBombCollision(int[] pos) {
        // Example collision area for Bombs
        return pos[0] < 50 && pos[1] < 50;
    }

    private boolean isBarrelCollision(int[] pos) {
        // Example collision area for Barrels
        return pos[0] > 50 && pos[0] < 100;
    }

    private boolean isBulletCollision(int[] pos) {
        // Example collision area for Bullets
        return pos[1] > 100;
    }

    private boolean isStarCollision(int[] pos) {
        // Example collision detection for Stars
        return pos[0] > 100 && pos[1] < 30; // Arbitrary collision logic for stars
    }
}