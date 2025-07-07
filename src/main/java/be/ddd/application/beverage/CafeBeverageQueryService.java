package be.ddd.application.beverage;

import be.ddd.api.dto.res.BeverageCountDto;
import be.ddd.api.dto.res.CafeBeverageDetailsDto;
import be.ddd.application.beverage.dto.CafeBeveragePageDto;
import be.ddd.common.dto.CursorPageResponse;
import be.ddd.domain.entity.crawling.CafeBrand;
import be.ddd.domain.entity.crawling.SugarLevel;
import java.util.Optional;
import java.util.UUID;

public interface CafeBeverageQueryService {
    CursorPageResponse<CafeBeveragePageDto> getCafeBeverageCursorPage(
            Long cursor,
            int size,
            Optional<CafeBrand> brandFilter,
            Optional<SugarLevel> sugarLevel,
            Long memberId);

    CafeBeverageDetailsDto getCafeBeverageByProductId(UUID productId);

    BeverageCountDto getBeverageCountByBrandAndSugarLevel(Optional<CafeBrand> brandFilter);
}
