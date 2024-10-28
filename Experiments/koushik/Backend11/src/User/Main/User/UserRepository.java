package User;

import org.springframework.data.jpa.repository.JpaRepository;
import src.Player;

public interface UserRepository extends JpaRepository<UserRepository, Long> {
    User findById(int id);

    //@Transactional
    void deleteById(int id);
}
