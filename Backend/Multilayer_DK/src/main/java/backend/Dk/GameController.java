package backend.Dk;

import backend.Lobby.LobbyService;
import backend.Lobby.PlayerUpdateMessage;
import backend.Players.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private LobbyService lobbyService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/updatePlayer")
    public void updatePlayer(PlayerUpdateMessage playerUpdateMessage) {
        //updating player state in the DB
        lobbyService.updatePlayerState(
                playerUpdateMessage.getLobbyID(),
                playerUpdateMessage.getPlayerId(),
                playerUpdateMessage.getX(),
                playerUpdateMessage.getY(),
                playerUpdateMessage.getScore()
        );

        //get updated list of players
        List<Long> players = lobbyService.getLobbyPlayers(playerUpdateMessage.getLobbyID());

        //Broadcast to the lobby channel
    }

    // PUT endpoint to update player's position and handle collisions
    @PutMapping("/move/{playerId}")
    public Player handlePlayerMovement(
            @PathVariable Long playerId,
            @RequestBody PlayerMovementRequest movementRequest) {

        // Retrieve the player from the database
        Player player = playerService.getPlayerById(playerId);
        if(player == null) {
            throw new RuntimeException("Player not found");
        }

        // Update player position from the request body
        int playerX = movementRequest.getPlayerX();
        int playerY = movementRequest.getPlayerY();

        // Call the collision service to handle score update
        collisionService.handlePlayerMovement(player, playerX, playerY);

        // Return the updated player with new score
        return playerService.savePlayer(player);  // Save player and return updated player
    }

    //TODO: not super sure why this is here
    @PostMapping("/move")
    public Player handlePlayerMovement(@RequestBody PlayerMovementRequest movementRequest) {
        //Retrieve player via playerID
        Long playerID = (long) movementRequest.getPlayerID();
        Player player = playerService.getPlayerById(playerID);
        if(player == null) {
            throw new RuntimeException("Player not found");
        }

        int playerX = movementRequest.getPlayerX();
        int playerY = movementRequest.getPlayerY();

        //calling collisionService to handle score updates based on new position
        collisionService.handlePlayerMovement(player, playerX, playerY);

        //Save and return updated player
        return playerService.savePlayer(player);
    }
}