/* Login page, all the player info will verified*/
package Player;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import onetoone.Laptops.Laptop;
import onetoone.Laptops.LaptopRepository;
import onetoone.Persons.Person;
import onetoone.Persons.PersonRepository;

@SpringBootApplication
class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    CommandLineRunner initPerson(PlayerRepository PlayerRepository) {
        return args ->
        {
            Player Player1 = new Person("player1", "password");
            Player Player2 = new Person("player2", "password");
            Player Player3 = new Person("player3", "password");
            PlayerRepository.save(Player1);
            PlayerRepository.save(Player2);
            PlayerRepository.save(Player3);
        };
    }
}