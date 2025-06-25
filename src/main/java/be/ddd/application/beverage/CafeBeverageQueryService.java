package be.ddd.application.beverage;

import be.ddd.application.beverage.dto.CafeBeverageDetailsDto;
import be.ddd.application.beverage.dto.CafeBeveragePageDto;
import be.ddd.common.dto.CursorPageResponse;
import java.util.UUID;

public interface CafeBeverageQueryService {
    CursorPageResponse<CafeBeveragePageDto> getCafeBeverageCursorPage(Long cursor, int size);

    CafeBeverageDetailsDto getCafeBeverageByProductId(UUID productId);
}
