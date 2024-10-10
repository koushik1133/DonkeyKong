package backend.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Sam Gumm
 * 
 */ 

public interface PersonRepository extends JpaRepository<Person, Long> {

    //template
//    Person findById(int id);
//
//    void deleteById(int id);
//
//    Person findByLaptop_Id(int id);
}
