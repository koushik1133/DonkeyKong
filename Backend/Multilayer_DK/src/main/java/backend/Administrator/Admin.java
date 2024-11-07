package backend.Administrator;
import backend.Players.Player;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 
 * @author Sam Gumm
 */

@Entity
public class Admin {
    //Getters and Setters for each field //
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String password;
    private String players;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Admin_id")
    private Admin admin;

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Admin() {

    }

    public long getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getname() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {this.admin = admin;}


    public String getPlayers() { return players; }
    public void setPlayers(Set<Player> players) {
        this.players = players.toString();
    }
}
