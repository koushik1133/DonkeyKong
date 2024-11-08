package backend.Scores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Score findById(long id);

    @Transactional
    void deleteById(long id);


}
