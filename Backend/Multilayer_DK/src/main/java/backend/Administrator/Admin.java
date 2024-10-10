package backend.Administrator;

import jakarta.persistence.*;

/**
 * 
 * @author Sam Gumm
 */ 

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Admin_id")
    private Admin admin;

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Admin() {

    }

    // =============================== Getters and Setters for each field ================================== //
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {this.admin = admin;}
    //WILL BE REPLACED WITH JPA???

}
