package Dk;

import backend.Dk.CollisionService;
import backend.Dk.PlayerService;
import backend.Players.Player;
import backend.Scores.Score;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KoushikSystemTest {

    private PlayerService playerService = new PlayerService();
    private CollisionService collisionService = new CollisionService();

    @Test
    void testPlayerCollisionWithBomb() {
        Player player = new Player("player1", "password");
        player.setScore(100);

        collisionService.detectCollision(player, ObstacleType.BOMB);

        assertEquals(50, player.getScore(), "Score should decrement by 50 on collision with Bomb");
    }

    @Test
    void testPlayerCollisionWithBarrel() {
        Player player = new Player("player2", "password");
        player.setScore(200);

        collisionService.handleCollision(player, ObstacleType.BARREL);

        assertEquals(100, player.getScore(), "Score should decrement by 100 on collision with Barrel");
    }

    @Test
    void testPlayerCollisionWithBullet() {
        Player player = new Player("player3", "password");
        player.setScore(300);

        collisionService.handleCollision(player, ObstacleType.BULLET);

        assertEquals(150, player.getScore(), "Score should decrement by 150 on collision with Bullet");
    }

    @Test
    void testPlayerCollectableCollision() {
        Player player = new Player("player4", "password");
        player.setScore(100);

        collisionService.handleCollectableCollision(player);

        assertEquals(110, player.getScore(), "Score should increment by 10 on collision with collectable");
    }
}
