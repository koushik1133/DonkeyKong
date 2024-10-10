package backend.Administrator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 
 * @author Sam Gumm
 * 
 */ 

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findById(Long id);

    @Transactional
    void deleteById(Long id);
}
