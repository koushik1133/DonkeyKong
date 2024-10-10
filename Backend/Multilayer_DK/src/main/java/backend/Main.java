package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * @author Sam Gumm
 * 
 */ 

@SpringBootApplication
@EnableJpaRepositories
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Create 3 Persons with their machines
    /**
     * 
     * @param PersonRepository repository for the Person entity
     * @param laptopRepository repository for the Laptop entity
     * Creates a commandLine runner to enter dummy data into the database
     * As mentioned in Person.java just associating the Laptop object with the Person will save it into the database because of the CascadeType
     */
//    @Bean
//    CommandLineRunner initPerson(PersonRepository, LaptopRepository laptopRepository) {
//        return args -> {
//            Person Person1 = new Person("John", "john@somemail.com");
//            Person Person2 = new Person("Jane", "jane@somemail.com");
//            Person Person3 = new Person("Justin", "justin@somemail.com");
//            Laptop laptop1 = new Laptop( 2.5, 4, 8, "Lenovo", 300);
//            Laptop laptop2 = new Laptop( 4.1, 8, 16, "Hp", 800);
//            Laptop laptop3 = new Laptop( 3.5, 32, 32, "Dell", 2300);
//            Person1.setLaptop(laptop1);
//            Person2.setLaptop(laptop2);
//            Person3.setLaptop(laptop3);
//            PersonRepository.save(Person1);
//            PersonRepository.save(Person2);
//            PersonRepository.save(Person3);
//
//        };
//    }

}
