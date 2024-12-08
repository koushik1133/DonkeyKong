package backend.Dk;

import backend.Players.Player;
import lombok.Getter;
import lombok.Setter;

public class PlayerMovementRequest {

    private Player player;
    // Getters and setters
    @Getter
    @Setter
    private int playerX;
    @Getter
    @Setter
    private int playerY;

    public Player getPlayer() {

        return null;
    }

}