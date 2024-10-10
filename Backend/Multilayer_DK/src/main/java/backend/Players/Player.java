package backend.Players;

import jakarta.persistence.*;

@Entity
public class Player{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a user object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the user table will have a field called laptop_id
     */

    /*
    * TODO: Decide on the relation to use here
    * */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Player_id")
    private Player player;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Player() {
    }


    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return username;
    }

    public void setName(String name){
        this.username = name;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

}