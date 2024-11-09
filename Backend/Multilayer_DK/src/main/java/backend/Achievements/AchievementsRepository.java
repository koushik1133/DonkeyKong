package backend.Achievements;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AchievementsRepository extends JpaRepository<Achievements, Long> {
}

