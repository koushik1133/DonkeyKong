package backend.Lobby;

import backend.Players.Player;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class LobbyBroadcastMessage {
    //Sends back a list of Player objects
    //TODO: might need to change to List<String> to keep in line with rest of code
    private List<Player> players;

    public LobbyBroadcastMessage(List<Player> players) {
        this.players = players;
    }

}
