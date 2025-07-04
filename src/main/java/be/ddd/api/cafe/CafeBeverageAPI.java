package be.ddd.api.cafe;

import be.ddd.application.beverage.CafeBeverageQueryService;
import be.ddd.application.beverage.dto.CafeBeverageDetailsDto;
import be.ddd.application.beverage.dto.CafeBeveragePageDto;
import be.ddd.common.dto.ApiResponse;
import be.ddd.common.dto.CursorPageResponse;
import be.ddd.common.util.StringBase64EncodingUtil;
import be.ddd.domain.entity.crawling.CafeBrand;
import jakarta.validation.constraints.Positive;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cafe-beverages")
@RequiredArgsConstructor
public class CafeBeverageAPI {

    private final CafeBeverageQueryService cafeBeverageQueryService;
    private final StringBase64EncodingUtil encodingUtil;

    @GetMapping
    public ApiResponse<CursorPageResponse<CafeBeveragePageDto>> getCafeBeverages(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "15") @Positive int size,
            @RequestParam(required = false) String cafeBrand) {
        Long decodedCursor =
                Optional.ofNullable(cursor).map(encodingUtil::decodeSignedCursor).orElse(0L);

        Optional<CafeBrand> brand =
                Optional.ofNullable(cafeBrand).flatMap(CafeBrand::findByDisplayName);

        CursorPageResponse<CafeBeveragePageDto> results =
                cafeBeverageQueryService.getCafeBeverageCursorPage(decodedCursor, size, brand);
        return ApiResponse.success(results);
    }

    @GetMapping("{productId}")
    public ApiResponse<?> getCafeBeverageByProductId(@PathVariable("productId") UUID productId) {
        CafeBeverageDetailsDto beverageDetails =
                cafeBeverageQueryService.getCafeBeverageByProductId(productId);
        return ApiResponse.success(beverageDetails);
    }
}
