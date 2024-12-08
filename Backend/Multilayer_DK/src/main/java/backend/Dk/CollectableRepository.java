package backend.Dk;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectableRepository extends JpaRepository<Collectable, Long> {
    public default List<Collectable> findAll() {
        return List.of();
    }
}



