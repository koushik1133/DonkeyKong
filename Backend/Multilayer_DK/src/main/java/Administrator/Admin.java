package Administrator;

//IMPORT STATEMENTS HERE
import jakarta.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Sam Gumm
 */ 

@Entity
public class Admin {
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //VARIABLES HERE

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(Person)
     * @JsonIgnore is to assure that there is no infinite loop while returning either Person/laptop objects (laptop->Person->laptop->...)
     */
    //@OneToOne
    //@JsonIgnore


    //CONSTRUCTOR HERE

    //Interface here

    // =============================== Getters and Setters for each field ================================== //
    //WILL BE REPLACED WITH JPA???

}
