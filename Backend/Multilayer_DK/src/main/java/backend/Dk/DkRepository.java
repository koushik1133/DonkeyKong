package backend.Dk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sam Gumm, Koushik
 */


public interface DkRepository extends JpaRepository<Dk_object, Long> {
    Dk_object findById(int id);

    @Transactional
    void deleteById(int id);


}
