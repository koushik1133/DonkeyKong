package backend.Lobby;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Lobby {
    private String lobbyID;
    private List<Long> players;

    public Lobby(String id) {
        this.lobbyID = id;
        this.players = new ArrayList<>();
    }


}
