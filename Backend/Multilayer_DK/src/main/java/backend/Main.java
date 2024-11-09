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

    /**
     * @param playerRepository repository for the Player entity
     * @param adminRepository  repo for the Admin entity
     * @param dkRepository     repo for the Dk entity
     * @param achievementRepository repo for the Achievements entity
     * @param scoreRepository repo for the Score entity
     */
    }