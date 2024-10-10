package backend.Administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * @author Sam Gumm
 * 
 */ 

@RestController
public class AdminController {

    @Autowired
    //LaptopRepository laptopRepository;
    AdminRepository adminRepository;
    
    private final String success = "{\"message\":\"success\"}";





    //MAPPINGS HERE:
    //READ (@GetMapping(path = "/<something here>)
    //EDIT (@PutMapping(...))
    //DELETE (@DeleteMapping(..))
    //ADD (@PostMapping(...))








    /**@GetMapping(path = "/laptops")
    List<Laptop> getAllLaptops(){
        return laptopRepository.findAll();
    }

    @GetMapping(path = "/laptops/{id}")
    Laptop getLaptopById(@PathVariable int id){
        return laptopRepository.findById(id);
    }

    @PostMapping(path = "/laptops")
    String createLaptop(@RequestBody Laptop Laptop){
        String failure = "{\"message\":\"failure\"}";
        if (Laptop == null)
            return failure;
        laptopRepository.save(Laptop);
        return success;
    }

    @PutMapping(path = "/laptops/{id}")
    Laptop updateLaptop(@PathVariable int id, @RequestBody Laptop request){
        Laptop laptop = laptopRepository.findById(id);
        if(laptop == null)
            return null;
        laptopRepository.save(request);
        return laptopRepository.findById(id);
    }

    @DeleteMapping(path = "/laptops/{id}")
    String deleteLaptop(@PathVariable int id){

        // Check if there is an object depending on Person and then remove the dependency
        Person person = personRepository.findByLaptop_Id(id);
        person.setLaptop(null);
        personRepository.save(person);

        // delete the laptop if the changes have not been reflected by the above statement
        laptopRepository.deleteById(id);
        return success;
    } */
}
