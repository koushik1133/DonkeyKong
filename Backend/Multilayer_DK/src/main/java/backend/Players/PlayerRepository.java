package backend.Players;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    static Player savePlayer(Player player) {
        return player;
    }
}
