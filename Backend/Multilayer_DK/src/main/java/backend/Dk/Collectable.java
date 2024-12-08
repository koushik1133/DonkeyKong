package backend.Dk;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Collectable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // e.g., fruit, coin, etc.
    private int xPosition;
    private int yPosition;
    private int scoreValue; // Value to increment score

    public int getScore() {
        return 0;
    }

    public int getYPosition() {
        return 0;
    }

    public int getXPosition() {
        return 0;
    }


    // Getters and setters omitted for brevity
}