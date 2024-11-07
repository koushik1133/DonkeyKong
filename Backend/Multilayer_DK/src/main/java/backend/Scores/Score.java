package backend.Scores;
import jakarta.persistence.*;
import backend.Players.Player;


@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // Unique identifier for the score

    private int score;  // The score value for the player

    @OneToOne
    @JoinColumn(name = "player_id")  // Foreign key column linking the score to a player
    private Player player;  // A score is tied to exactly one player

    // Default constructor
    public Score(int i) {
        this. = i;
    }

    // Constructor with parameters (excluding id, as it will be auto-generated)
    public Score(int score, Player player) {
        this.score = score;
    }

    public Score() {

    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}