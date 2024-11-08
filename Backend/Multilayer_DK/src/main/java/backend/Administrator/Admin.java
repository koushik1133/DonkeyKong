package backend.Administrator;
import backend.Players.Player;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Sam Gumm
 */

@Setter
@Getter
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;

    // One-to-Many relationship: One Admin can have many Players
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true) // mappedBy refers to the 'admin' field in the Player class
    @JsonManagedReference
    private List<Player> players = new ArrayList<>();

    //helper method to manage the bidirectional relationship
    public void addPlayer(Player player) {
        players.add(player);
        player.setAdmin(this);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.setAdmin(null);
    }

    // Default constructor
    public Admin() {
    }

    // Constructor with fields
    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

}