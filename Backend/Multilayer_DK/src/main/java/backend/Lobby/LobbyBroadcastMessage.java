package backend.Lobby;

import backend.Players.Player;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class LobbyBroadcastMessage {
    private List<Player> players;

    public LobbyBroadcastMessage(List<Player> players) {
        this.players = players;
    }

}
