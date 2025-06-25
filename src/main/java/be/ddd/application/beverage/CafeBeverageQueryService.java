package be.ddd.application.beverage;

import be.ddd.application.beverage.dto.CafeBeverageDto;
import be.ddd.common.dto.CursorPageResponse;

public interface CafeBeverageQueryService {
    CursorPageResponse<CafeBeverageDto> getCafeBeverageCursorPage(Long cursor, int size);
}
