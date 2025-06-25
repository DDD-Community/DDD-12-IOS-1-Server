package be.ddd.application.beverage;

import be.ddd.application.beverage.dto.CafeBeveragePageDto;
import be.ddd.common.dto.CursorPageResponse;

public interface CafeBeverageQueryService {
    CursorPageResponse<CafeBeveragePageDto> getCafeBeverageCursorPage(Long cursor, int size);
}
