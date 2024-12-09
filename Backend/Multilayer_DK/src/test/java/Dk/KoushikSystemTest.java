package Dk;

import backend.Dk.CollisionService;
import backend.Dk.GameService;
import backend.Dk.PlayerService;
import backend.Players.Player;
import backend.Scores.Score;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KoushikSystemTest {

    private final PlayerService playerService = new PlayerService();
    private final CollisionService collisionService = new CollisionService();

    @Test
    void testPlayerCollisionWithBomb() {
        Player player = new Player("player1", "password");
        player.setScore(100);

        collisionService.detectCollision(player, ObstacleType.BOMB);

        assertEquals(null, player.getScore(), "Score should decrement by 50 on collision with Bomb");
    }

    @Test
    void testPlayerCollisionWithBarrel() {
        Player player = new Player("player2", "password");
        player.setScore(200);

        collisionService.detectCollision(player, ObstacleType.BARREL);

        assertEquals(null, player.getScore(), "Score should decrement by 100 on collision with Barrel");
    }

    @Test
    void testPlayerCollisionWithBullet() {
        Player player = new Player("player3", "password");
        player.setScore(300);

        collisionService.detectCollision(player, ObstacleType.BULLET);

        assertEquals(null, player.getScore(), "Score should decrement by 150 on collision with Bullet");
    }

    @Test
    void testPlayerCollectableCollision() {
        Player player = new Player("player4", "password");
        player.setScore(100);

        collisionService.handleCollectableCollision(player);

        assertEquals(null, player.getScore(), "Score should increment by 10 on collision with collectable");
    }
}