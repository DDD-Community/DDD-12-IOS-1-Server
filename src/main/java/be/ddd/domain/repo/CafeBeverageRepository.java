package be.ddd.domain.repo;

import be.ddd.domain.entity.crawling.CafeBeverage;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface CafeBeverageRepository extends Repository<CafeBeverage, Long> {
    Optional<CafeBeverage> findByName(String name);

    <S extends CafeBeverage> void saveAll(Iterable<S> entities);

    List<CafeBeverage> findByIdGreaterThanOrderByIdAsc(Long cursor, Pageable pageable);

    Optional<CafeBeverage> findByProductId(UUID productId);
}
