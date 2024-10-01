package onetoone.Laptops;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import onetoone.Persons.Person;
import onetoone.Persons.PersonRepository;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@RestController
public class LaptopController {

    final
    LaptopRepository laptopRepository;

    final
    PersonRepository personRepository;
    
    private final String success = "{\"message\":\"success\"}";

    public LaptopController(LaptopRepository laptopRepository, PersonRepository personRepository) {
        this.laptopRepository = laptopRepository;
        this.personRepository = personRepository;
    }

    @GetMapping(path = "/Laptops")
    List<Laptop> getAllLaptops(){
        return laptopRepository.findAll();
    }

    @GetMapping(path = "/Laptops/{id}")
    Laptop getLaptopById(@PathVariable int id){
        return laptopRepository.findById(id);
    }

    @PostMapping(path = "/Laptops")
    String createLaptop(@RequestBody Laptop Laptop){
        String failure = "{\"message\":\"failure\"}";
        if (Laptop == null)
            return failure;
        laptopRepository.save(Laptop);
        return success;
    }

    @PutMapping(path = "/Laptops/{id}")
    Laptop updateLaptop(@PathVariable int id, @RequestBody Laptop request){
        Laptop laptop = laptopRepository.findById(id);
        if(laptop == null)
            return null;
        laptopRepository.save(request);
        return laptopRepository.findById(id);
    }

    @DeleteMapping(path = "/Laptops/{id}")
    String deleteLaptop(@PathVariable int id){

        // Check if there is an object depending on Person and then remove the dependency
        Person person = personRepository.findByLaptop_Id(id);
        person.setLaptop(null);
        personRepository.save(person);

        // delete the laptop if the changes have not been reflected by the above statement
        laptopRepository.deleteById(id);
        return success;
    }
}
