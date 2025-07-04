package be.ddd.application.beverage;

import be.ddd.application.beverage.dto.CafeBeverageDetailsDto;
import be.ddd.application.beverage.dto.CafeBeveragePageDto;
import be.ddd.application.beverage.dto.CafeStoreDto;
import be.ddd.common.dto.CursorPageResponse;
import be.ddd.common.util.StringBase64EncodingUtil;
import be.ddd.domain.entity.crawling.CafeBeverage;
import be.ddd.domain.entity.crawling.CafeBrand;
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
    private final CafeBeverageRepository cafeBeverageRepository;

    @Override
    public CursorPageResponse<CafeBeveragePageDto> getCafeBeverageCursorPage(
            Long cursor, int size, Optional<CafeBrand> brandFilter) {

        CafeBrand brand = brandFilter.orElse(null);

        List<CafeBeverage> fetched = cafeBeverageRepository.findWithCursor(cursor, size + 1, brand);

        boolean hasNext = fetched.size() > size;

        String nextCursor = null;
        if (hasNext) {
            nextCursor = encodingUtil.encodeSignedCursor(fetched.get(size - 1).getId());
        }

        List<CafeBeveragePageDto> pageResults =
                fetched.stream().limit(size).map(CafeBeveragePageDto::from).toList();

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
}
