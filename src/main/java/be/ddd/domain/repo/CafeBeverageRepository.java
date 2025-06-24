package be.ddd.domain.repo;

import be.ddd.domain.entity.crawling.CafeBeverage;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface CafeBeverageRepository extends Repository<CafeBeverage, Long> {
    Optional<CafeBeverage> findByName(String name);

    <S extends CafeBeverage> void saveAll(Iterable<S> entities);
}
