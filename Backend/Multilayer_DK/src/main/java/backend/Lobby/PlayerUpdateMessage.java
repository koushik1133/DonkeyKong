package backend.Lobby;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerUpdateMessage {
    private Long playerId;
    private String lobbyID;
    private int x;
    private int y;
    private int score;
}
