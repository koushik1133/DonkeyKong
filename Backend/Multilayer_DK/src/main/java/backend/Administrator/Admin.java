package backend.Administrator;
import backend.Players.Player;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//import javax.persistence.*;
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
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL) // mappedBy refers to the 'admin' field in the Player class
    private List<Player> players;

    // Default constructor
    public Admin() {
    }

    // Constructor with fields
    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

}