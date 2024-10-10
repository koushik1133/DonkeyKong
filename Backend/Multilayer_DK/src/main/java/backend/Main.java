package backend;

import backend.Administrator.Admin;
import backend.Administrator.AdminRepository;
import backend.Players.Player;
import backend.Players.PlayerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
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
     * @param playerRepository repository for the Player entity
     * @param adminRepository repo for the Admin entity
     * Creates a commandLine runner to enter dummy data into the database
     */
    @Bean
    CommandLineRunner initPerson(PlayerRepository playerRepository, AdminRepository adminRepository) {
        return args -> {
            Player player5 = new Player("fake", "")
            Player player1 = new Player("Koushik", "1234");
            Player player2 = new Player("Sam", "smgumm@fake.emial", "1234");
            Player player3 = new Player("Nick", "nick@fake.emial", "1234");
            Player player4 = new Player("Alex", "alex@fake.emial", "1234");
            Admin admin1 = new Admin("admin1", "admin1@fake.emial", "1234");
            Person1.setLaptop(laptop1);
            Person2.setLaptop(laptop2);
            Person3.setLaptop(laptop3);
            PersonRepository.save(Person1);
            PersonRepository.save(Person2);
            PersonRepository.save(Person3);

        };
    }

}
