package backend.Dk;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public class ObstacleRepository {
    public List<Obstacle> findAll() {
        return List.of();
    }
}
