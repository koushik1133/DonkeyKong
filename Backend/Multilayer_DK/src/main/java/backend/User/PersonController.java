package backend.User;

import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sam Gumm
 *
 */



//Template to use
@RestController
public class PersonController {

//    @Autowired
//    PersonRepository PersonRepository;
//
//    @Autowired
//    LaptopRepository laptopRepository;
//
//    private final String success = "{\"message\":\"success\"}";
//    private final String failure = "{\"message\":\"failure\"}";
//
//    @GetMapping(path = "/Persons")
//    List<Person> getAllPersons(){
//        return PersonRepository.findAll();
//    }
//
//    @GetMapping(path = "/Persons/{id}")
//    Person getPersonById( @PathVariable int id){
//        return PersonRepository.findById(id);
//    }
//
//    @PostMapping(path = "/Persons")
//    String createPerson(@RequestBody Person Person){
//        if (Person == null)
//            return failure;
//        PersonRepository.save(Person);
//        return success;
//    }
//
//    /* not safe to update */
////    @PutMapping("/Persons/{id}")
////    Person updatePerson(@PathVariable int id, @RequestBody Person request){
////        Person Person = PersonRepository.findById(id);
////        if(Person == null)
////            return null;
////        PersonRepository.save(request);
////        return PersonRepository.findById(id);
////    }
//
//    @PutMapping("/Persons/{id}")
//    Person updatePerson(@PathVariable int id, @RequestBody Person request){
//        Person Person = PersonRepository.findById(id);
//
//        if(Person == null) {
//            throw new RuntimeException("Person id does not exist");
//        }
//        else if (Person.getId() != id){
//            throw new RuntimeException("path variable id does not match Person request id");
//        }
//
//        PersonRepository.save(request);
//        return PersonRepository.findById(id);
//    }
//
//    @PutMapping("/Persons/{PersonId}/laptops/{laptopId}")
//    String assignLaptopToPerson(@PathVariable int PersonId,@PathVariable int laptopId){
//        Person Person = PersonRepository.findById(PersonId);
//        Laptop laptop = laptopRepository.findById(laptopId);
//        if(Person == null || laptop == null)
//            return failure;
//        laptop.setPerson(Person);
//        Person.setLaptop(laptop);
//        PersonRepository.save(Person);
//        return success;
//    }
//
//    @DeleteMapping(path = "/Persons/{id}")
//    String deletePerson(@PathVariable int id){
//        Person Person = PersonRepository.findById(id);
//        if(Person == null)
//            return failure;
//        else {
//            PersonRepository.delete(Person);
//            return success;
//        }
//    }
}
