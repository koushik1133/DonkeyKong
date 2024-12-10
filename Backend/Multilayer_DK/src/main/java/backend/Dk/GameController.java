package backend.Dk;

import backend.Players.Player;
import backend.Players.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    public Player player;


    // PUT endpoint to update player's position and handle collisions
    @PutMapping("/move/{playerId}")
    public Player handlePlayerMovement(
            @PathVariable Long playerId,
            @RequestBody Player movementRequest) {

        // Update player position from the request body
        int playerX = movementRequest.getPlayerX();
        int playerY = movementRequest.getPlayerY();

        return PlayerRepository.savePlayer(player);  // Save player and return updated player
    }

    @PostMapping("/move")
    public void handlePlayerMovement(@RequestBody Player movementRequest) {
        Player player = movementRequest.getPlayer();
        int playerX = movementRequest.getPlayerX();
        int playerY = movementRequest.getPlayerY();
    }
}