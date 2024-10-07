package onetoone.Laptops;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Sam Gumm
 * 
 */ 

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findById(int id);

    @Transactional
    void deleteById(int id);
}
