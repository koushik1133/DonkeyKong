package backend;

import backend.Administrator.Admin;
import backend.Administrator.AdminRepository;
import backend.Dk.Dk_object;
import backend.Dk.DkRepository;
import backend.Players.Player;
import backend.Players.PlayerRepository;
import backend.Scores.Score;
import backend.Scores.ScoreRepository;
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

    /**
     * @param playerRepository repository for the Player entity
     * @param adminRepository  repo for the Admin entity
     * @param dkRepository     repo for the Dk entity
     *                         Creates a commandLine runner to enter dummy data into the database
     * @param scoreRepository repo for the Score entity
     */
    @Bean
    CommandLineRunner initPerson(PlayerRepository playerRepository, AdminRepository adminRepository, DkRepository dkRepository, ScoreRepository scoreRepository) {
        return args -> {
            Player player1 = new Player("Koushik", "1234");
            Player player2 = new Player("Sam", "1234");
            Player player3 = new Player("Nick", "1234");
            Player player4 = new Player("Alex", "1234");
            Admin admin1 = new Admin("admin1", "admin1");
            Dk_object object1 = new Dk_object("bomb",50);
            Dk_object object2 = new Dk_object("bullet",40);
            Dk_object object3 = new Dk_object("barrel",30);
            Score score1 = new Score(150);
            Score score2 = new Score(200);
            Score score3 = new Score(250);
            Score score4 = new Score(300);
            adminRepository.save(admin1);
            playerRepository.save(player1);
            playerRepository.save(player2);
            playerRepository.save(player3);
            playerRepository.save(player4);
            dkRepository.save(object1);
            dkRepository.save(object2);
            dkRepository.save(object3);
            scoreRepository.save(score1);
            scoreRepository.save(score2);
            scoreRepository.save(score3);
            scoreRepository.save(score4);
        };
    }

}

