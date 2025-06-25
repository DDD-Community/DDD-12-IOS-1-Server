package be.ddd.api.cafe;

import be.ddd.application.beverage.CafeBeverageQueryService;
import be.ddd.application.beverage.dto.CafeBeveragePageDto;
import be.ddd.common.dto.ApiResponse;
import be.ddd.common.dto.CursorPageResponse;
import be.ddd.common.util.StringBase64EncodingUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cafe-beverages")
@RequiredArgsConstructor
public class CafeBeverageAPI {

    private final CafeBeverageQueryService cafeBeverageQueryService;
    private final StringBase64EncodingUtil encodingUtil;

    @GetMapping
    public ApiResponse<CursorPageResponse<CafeBeveragePageDto>> getCafeBeverages(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "15") int size) {
        Long decodedCursor =
                Optional.ofNullable(cursor).map(encodingUtil::decodeSignedCursor).orElse(0L);

        CursorPageResponse<CafeBeveragePageDto> results =
                cafeBeverageQueryService.getCafeBeverageCursorPage(decodedCursor, size);
        return ApiResponse.success(results);
    }
}
