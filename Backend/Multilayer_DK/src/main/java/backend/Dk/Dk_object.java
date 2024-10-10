package backend.Dk;

import jakarta.persistence.*;
@Entity
public class Dk_object{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String object;
    private int damage;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a user object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the user table will have a field called laptop_id
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Dk_id")
    private Dk_object dk_obj;

    public Dk_object(String object, int damage) {
        this.object = object;
        this.damage = damage;
    }


    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return object;
    }

    public void setName(String name){
        this.object = name;
    }

    public int getDamage(){
        return damage;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

}