package Player;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.transaction.annotation.Transactional;
import src.Player;
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findById(int id);

    //@Transactional
    void deleteById(int id);
}
