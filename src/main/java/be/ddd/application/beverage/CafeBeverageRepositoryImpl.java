package be.ddd.application.beverage;

import be.ddd.domain.entity.crawling.CafeBeverage;
import be.ddd.domain.entity.crawling.CafeBrand;
import be.ddd.domain.entity.crawling.QCafeBeverage;
import be.ddd.domain.repo.CafeBeverageRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CafeBeverageRepositoryImpl implements CafeBeverageRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QCafeBeverage beverage = QCafeBeverage.cafeBeverage;

    @Override
    public List<CafeBeverage> findWithCursor(Long cursor, int limit, CafeBrand brand) {
        return queryFactory
                .selectFrom(beverage)
                .where(beverage.id.gt(cursor), brand != null ? brandEq(brand) : null)
                .orderBy(beverage.id.asc())
                .limit(limit)
                .fetch();
    }

    private BooleanExpression brandEq(CafeBrand b) {
        return beverage.cafeStore.cafeBrand.eq(b);
    }
}
