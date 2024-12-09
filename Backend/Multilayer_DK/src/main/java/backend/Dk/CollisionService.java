package backend.Dk;

import backend.Players.Player;
import backend.Scores.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollisionService {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ObstacleRepository obstacleRepository;

    @Autowired
    private CollectableRepository collectableRepository;

    // Check for collision between player and an object (obstacle or collectable)
    private boolean detectCollision(int playerX, int playerY, int objectX, int objectY, int objectWidth, int objectHeight) {
        return playerX < objectX + objectWidth &&
                playerX + 30 > objectX &&
                playerY < objectY + objectHeight &&
                playerY + 50 > objectY; // Player dimensions assumed as 30x50
    }

    // Handle player movement and check for collision
    public void handlePlayerMovement(Player player, int playerX, int playerY) {
        // Check collision with obstacles
        List<Obstacle> obstacles = obstacleRepository.findAll();
        for (Obstacle obstacle : obstacles) {
            if (detectCollision(playerX, playerY, obstacle.getXPosition(), obstacle.getYPosition(), 50, 50)) {
                // If collision occurs, decrement score based on the obstacle
                Score score = player.getScore();
                score.setScore(score.getScore() - obstacle.getDamage());
                player.setScore(score);
                playerService.savePlayer(player); // Persist player with updated score
            }
        }

        // Check collision with collectables
        List<Collectable> collectables = collectableRepository.findAll();
        for (Collectable collectable : collectables) {
            if (detectCollision(playerX, playerY, collectable.getXPosition(), collectable.getYPosition(), 30, 30)) {
                // If collectable is collected, increment score
                Score score = player.getScore();
                score.setScore(score.getScore() + collectable.getScore());
                player.setScore(score);
                playerService.savePlayer(player); // Persist player with updated score
            }
        }
    }

    public void handleCollectableCollision(Player player) {
    }

    public void detectCollision(Player player, Dk.ObstacleType obstacleType) {

    }
}
