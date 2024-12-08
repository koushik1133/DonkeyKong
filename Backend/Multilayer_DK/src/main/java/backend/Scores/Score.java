package backend.Scores;
import jakarta.persistence.*;
import backend.Players.Player;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Score {

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // Unique identifier for the score

    private int score;  // The score value for the player

    @OneToOne
    @JoinColumn(name = "player_id")  // Foreign key column linking the score to a player
    private Player player;  // A score is tied to exactly one player


    // Constructor with parameters (excluding id, as it will be auto-generated)
    public Score(int score) {
        this.score= score;
    }

    public Score() {

    }

    public int getScoreValue() {
        return score;
    }
}