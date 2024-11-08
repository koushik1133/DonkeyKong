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
/*
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String password;
    private String players;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Admin_id")
    private Admin admin;


    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Admin() {

    }

    public Long getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }
    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        this.password = password;
    }

    //public String getAdmin() {return admin;}

    //public void setAdmin(Admin admin) {this.admin = admin;}


    public void getPlayers() { return players; }


    public void setPlayers(Set<Player> players) {
        this.players = players.toString();
    }
}
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