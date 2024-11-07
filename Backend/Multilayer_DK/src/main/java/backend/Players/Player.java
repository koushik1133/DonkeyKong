package backend.Players;
import jakarta.persistence.*;
import backend.Scores.Score;
import backend.Administrator.Admin;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique identifier for the player

    private String username;  // Username of the player
    private String password;  // Password of the player (should be hashed in production)

    @ManyToOne
    @JoinColumn(name = "admin_id")  // Foreign key for Administration
    private Admin admin;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Score score;  // Each player has exactly one score (one-to-one relationship)

    // Default constructor
    public Player() {}

    // Constructor with parameters (excluding id, as it will be auto-generated)
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Admin getAdmin() { return admin; }
    public void setAdmin(Admin admin) { this.admin = admin; }
}