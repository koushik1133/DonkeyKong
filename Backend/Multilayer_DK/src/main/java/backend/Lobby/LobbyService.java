package backend.Lobby;

import backend.Players.PlayerRepository;
import backend.Scores.Score;
import backend.Scores.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LobbyService {
    private final Map<String, Lobby> lobbies = new ConcurrentHashMap<>();
    private final PlayerRepository playerRepository;
    private final ScoreRepository scoreRepository;

    /**
     *
     * @param playerRepository
     * @param scoreRepository
     */
    public LobbyService(PlayerRepository playerRepository, ScoreRepository scoreRepository) {
        this.playerRepository = playerRepository;
        this.scoreRepository = scoreRepository;
    }

    /**
     *
     * @param lobbyID
     * @return
     */
    public Lobby createLobby(String lobbyID) {
        Lobby lobby = new Lobby(lobbyID);
        lobbies.put(lobbyID, lobby);
        return lobby;
    }

    /**
     *
     * @param lobbyID
     * @param playerID
     * @return
     */
    public Lobby joinLobby(String lobbyID, Long playerID) {
        Lobby lobby = lobbies.get(lobbyID);

        //checking if lobby exists
        if(lobby == null) {
            throw new RuntimeException("Lobby not found");
        }

        //checking if player also exists
        playerRepository.findById(playerID).orElseThrow(() -> new RuntimeException("Player not found"));

        //adding player to lobby
        if(!lobby.getPlayers().contains(playerID)) {
            lobby.getPlayers().add(playerID);
        }
        return lobby;
    }

    /**
     *
     * @param lobbyID
     * @return
     */
    public List<Long> getLobbyPlayers(String lobbyID) {
        Lobby lobby = lobbies.get(lobbyID);
        if(lobby == null) {
            throw new RuntimeException("Lobby not found");
        }

        //returns a list of playerIDs
        return lobby.getPlayers();
    }

    /**
     *
     * @param lobbyID ID of lobby the player to be updated is in
     * @param playerID ID of the player to update
     * @param x new X-Coordinate
     * @param y new Y-Coordinate
     * @param scoreValue new Score
     */
    public void updatePlayerState(String lobbyID, Long playerID, int x, int y, int scoreValue) {
        Lobby lobby = lobbies.get(lobbyID);
        //if lobby is empty return
        if(lobby == null) return;
        //if lobby does not contain the player return
        if(!lobby.getPlayers().contains(playerID)) return;

        //Trawls through playerRepo for player with matching ID
        playerRepository.findById(playerID).ifPresent(p -> {
            p.setXPosition(x);
            p.setYPosition(y);

            Score s = p.getScore();
            if(s != null) {
                s.setScore(scoreValue);
                scoreRepository.save(s);
            } else {
                Score newScore = new Score(scoreValue);
                newScore.setPlayer(p);
                p.setScore(newScore);
            }
            playerRepository.save(p);
        });
    }
}
