package backend.Lobby;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lobbies")
public class LobbyController {

    @Autowired
    private LobbyService lobbyService;

    /**
     * TODO: decide whether this can just be a string or something
     * @param request
     * @return
     */
    @PostMapping("/create")
    public Lobby create(@RequestBody Map<String, String> request) {
        String lobbyID = request.get("lobbyID");
        return lobbyService.createLobby(lobbyID);
    }

    /**
     *
     * @param request
     * @return
     */
    @PostMapping("/join")
    public Lobby join(@RequestBody Map<String, String> request) {
        String lobbyID = request.get("lobbyID");
        Long playerID = Long.parseLong(request.get("playerID"));
        return lobbyService.joinLobby(lobbyID, playerID);
    }

    /**
     *
     * @param lobbyID id of the lobby to interact with
     * @return Returns a LIST OF PLAYER IDS, not player objects
     */
    @GetMapping("/{lobbyID}")
    public List<Long> getPlayers(@PathVariable("lobbyID") String lobbyID) {
        return lobbyService.getLobbyPlayers(lobbyID);
    }
}
