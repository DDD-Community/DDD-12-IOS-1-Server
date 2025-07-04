package be.ddd.domain.repo;

import be.ddd.domain.entity.crawling.CafeBeverage;
import be.ddd.domain.entity.crawling.CafeBrand;
import jakarta.annotation.Nullable;
import java.util.List;

public interface CafeBeverageRepositoryCustom {
    List<CafeBeverage> findWithCursor(Long cursor, int limit, @Nullable CafeBrand brand);
}
