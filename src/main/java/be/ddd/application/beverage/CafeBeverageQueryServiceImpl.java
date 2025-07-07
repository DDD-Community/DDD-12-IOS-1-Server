package be.ddd.application.beverage;

import be.ddd.application.beverage.dto.BeverageCountDto;
import be.ddd.application.beverage.dto.CafeBeverageDetailsDto;
import be.ddd.application.beverage.dto.CafeBeveragePageDto;
import be.ddd.application.beverage.dto.CafeStoreDto;
import be.ddd.common.dto.CursorPageResponse;
import be.ddd.common.util.StringBase64EncodingUtil;
import be.ddd.domain.entity.crawling.CafeBeverage;
import be.ddd.domain.entity.crawling.CafeBrand;
import be.ddd.domain.entity.crawling.SugarLevel;
import be.ddd.domain.exception.CafeBeverageNotFoundException;
import be.ddd.domain.repo.CafeBeverageRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeBeverageQueryServiceImpl implements CafeBeverageQueryService {
    private final CafeBeverageRepository beverageRepository;
    private final StringBase64EncodingUtil encodingUtil;

    @Override
    public CursorPageResponse<CafeBeveragePageDto> getCafeBeverageCursorPage(
            Long cursor,
            int size,
            Optional<CafeBrand> brandFilter,
            Optional<SugarLevel> sugarLevel,
            Long memberId) {

        CafeBrand brand = brandFilter.orElse(null);
        SugarLevel sugar = sugarLevel.orElse(null);

        List<CafeBeveragePageDto> fetched =
                beverageRepository.findWithCursor(cursor, size + 1, brand, sugar, memberId);

        boolean hasNext = fetched.size() > size;

        String nextCursor = null;
        if (hasNext) {
            nextCursor = encodingUtil.encodeSignedCursor(fetched.get(size - 1).id());
        }

        List<CafeBeveragePageDto> pageResults = fetched.stream().limit(size).toList();

        return new CursorPageResponse<>(pageResults, nextCursor, hasNext);
    }

    @Override
    public CafeBeverageDetailsDto getCafeBeverageByProductId(UUID productId) {
        CafeBeverage fetch =
                beverageRepository
                        .findByProductId(productId)
                        .orElseThrow(CafeBeverageNotFoundException::new);

        return CafeBeverageDetailsDto.from(
                fetch.getName(),
                fetch.getProductId(),
                fetch.getImgUrl(),
                fetch.getBeverageType(),
                fetch.getBeverageNutrition(),
                new CafeStoreDto(fetch.getCafeStore().getCafeBrand()));
    }

    @Override
    public BeverageCountDto getBeverageCountByBrandAndSugarLevel(Optional<CafeBrand> brandFilter) {

        CafeBrand brand = brandFilter.orElse(null);

        long sugarAllCount = beverageRepository.countAll(brand);
        long sugarZeroCount = beverageRepository.countBySugar(brand, SugarLevel.ZERO);
        long sugarLowCount = beverageRepository.countBySugar(brand, SugarLevel.LOW);

        return new BeverageCountDto(sugarAllCount, sugarZeroCount, sugarLowCount);
    }
}
