package backend.Players;
import backend.Achievements.Achievements;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import backend.Scores.Score;
import backend.Administrator.Admin;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Player {

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique identifier for the player
    private String username;  // Username of the player
    private String password;  // Password of the player
    private String sprite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")  // Foreign key for Administration
    @JsonBackReference
    private Admin admin;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Score score;  // Each player has exactly one score (one-to-one relationship)

    @ManyToMany
    @JoinTable(
            name = "player_achievement",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_id")
    )
    private Set<Achievements> achievements = new HashSet<>();


    // Default constructor
    public Player(String player1, String pass123) {}

    // Constructor with parameters (excluding id, as it will be auto-generated)
    public Player(String username, String password, String sprite) {
        this.username = username;
        this.password = password;
        this.sprite = sprite;
    }

    public Player() {

    }

    //helper methods for the relationship
    public void addAchievement(Achievements achievement) {
        this.achievements.add(achievement);
        this.getPlayers().add(this);
    }

    private Collection<Player> getPlayers() {
        return java.util.List.of();
    }

    public void removeAchievement(Achievements achievement) {
        this.achievements.remove(achievement);
        this.getPlayers().remove(this);
    }
    private int xPosition;
    private int yPosition;
}