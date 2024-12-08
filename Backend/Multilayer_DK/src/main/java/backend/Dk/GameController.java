package backend.Dk;

import backend.Players.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private CollisionService collisionService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ObstacleRepository obstacleRepository;

    @Autowired
    private CollectableRepository collectableRepository;

    // PUT endpoint to update player's position and handle collisions
    @PutMapping("/move/{playerId}")
    public Player handlePlayerMovement(
            @PathVariable Long playerId,
            @RequestBody PlayerMovementRequest movementRequest) {

        // Retrieve the player from the database
        Player player = playerService.getPlayerById(playerId);

        // Update player position from the request body
        int playerX = movementRequest.getPlayerX();
        int playerY = movementRequest.getPlayerY();

        // Call the collision service to handle score update
        collisionService.handlePlayerMovement(player, playerX, playerY);

        // Return the updated player with new score
        return playerService.savePlayer(player);  // Save player and return updated player
    }

    @PostMapping("/move")
    public void handlePlayerMovement(@RequestBody PlayerMovementRequest movementRequest) {
        Player player = movementRequest.getPlayer();
        int playerX = movementRequest.getPlayerX();
        int playerY = movementRequest.getPlayerY();
    }
}