package be.ddd.application.beverage;

import be.ddd.application.beverage.dto.CafeBeverageDetailsDto;
import be.ddd.application.beverage.dto.CafeBeveragePageDto;
import be.ddd.common.dto.CursorPageResponse;
import be.ddd.domain.entity.crawling.CafeBrand;
import java.util.Optional;
import java.util.UUID;

public interface CafeBeverageQueryService {
    CursorPageResponse<CafeBeveragePageDto> getCafeBeverageCursorPage(
            Long cursor, int size, Optional<CafeBrand> brandFilter);

    CafeBeverageDetailsDto getCafeBeverageByProductId(UUID productId);
}
