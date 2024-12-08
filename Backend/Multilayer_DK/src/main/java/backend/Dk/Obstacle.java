package backend.Dk;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Obstacle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // bomb, barrel, or bullet
    private int xPosition;
    private int yPosition;
    private int damage; // 50 for bomb, 100 for barrel, 150 for bullet

    public int getDamage() {
        return 0;
    }

    public int getXPosition() {
        return 0;
    }

    public int getYPosition() {
        return 0;

    }

    // Getters and setters omitted for brevity
}