package backend.Achievements;

import backend.Players.Player;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class Achievements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToMany(mappedBy = "achievements")
    @JsonBackReference
    private Set<Player> players = new HashSet<>();


    public Achievements() {}

    public Achievements(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
