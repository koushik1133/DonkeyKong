package backend.Administrator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Sam Gumm
 * 
 */ 

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findById(Long id);

    @Transactional
    void deleteById(Long id);
}
