package be.ddd.api.cafe;

import be.ddd.application.beverage.BeverageLikeService;
import be.ddd.application.beverage.CafeBeverageQueryService;
import be.ddd.application.beverage.dto.BeverageCountDto;
import be.ddd.application.beverage.dto.CafeBeverageDetailsDto;
import be.ddd.application.beverage.dto.CafeBeveragePageDto;
import be.ddd.common.dto.ApiResponse;
import be.ddd.common.dto.CursorPageResponse;
import be.ddd.common.util.StringBase64EncodingUtil;
import be.ddd.domain.entity.crawling.CafeBrand;
import be.ddd.domain.entity.crawling.SugarLevel;
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
    private final BeverageLikeService beverageLikeService;
    private final StringBase64EncodingUtil encodingUtil;
    private final Long MEMBER_ID = 1L;

    @GetMapping
    public ApiResponse<CursorPageResponse<CafeBeveragePageDto>> getCafeBeverages(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "15") @Positive int size,
            @RequestParam(required = false) String cafeBrand,
            @RequestParam(required = false) String sugarLevel) {
        Long decodedCursor =
                Optional.ofNullable(cursor).map(encodingUtil::decodeSignedCursor).orElse(0L);

        Optional<CafeBrand> brand =
                Optional.ofNullable(cafeBrand).flatMap(CafeBrand::findByDisplayName);

        Optional<SugarLevel> sugar = SugarLevel.fromParam(sugarLevel);

        CursorPageResponse<CafeBeveragePageDto> results =
                cafeBeverageQueryService.getCafeBeverageCursorPage(
                        decodedCursor, size, brand, sugar, MEMBER_ID);
        return ApiResponse.success(results);
    }

    @GetMapping("{productId}")
    public ApiResponse<?> getCafeBeverageByProductId(@PathVariable("productId") UUID productId) {
        CafeBeverageDetailsDto beverageDetails =
                cafeBeverageQueryService.getCafeBeverageByProductId(productId);
        return ApiResponse.success(beverageDetails);
    }

    @GetMapping("count")
    public ApiResponse<?> getBeverageCountByBrandAndSugarLevel(
            @RequestParam(required = false) String cafeBrand) {
        Optional<CafeBrand> brand =
                Optional.ofNullable(cafeBrand).flatMap(CafeBrand::findByDisplayName);
        BeverageCountDto countDto =
                cafeBeverageQueryService.getBeverageCountByBrandAndSugarLevel(brand);
        return ApiResponse.success(countDto);
    }

    @PostMapping("{beverageId}/like")
    public ApiResponse<?> likeBeverage(
            @PathVariable Long beverageId,
            @RequestParam Long memberId) { // TODO: get memberId from security context
        beverageLikeService.likeBeverage(memberId, beverageId);
        return ApiResponse.success("ok");
    }

    @DeleteMapping("{beverageId}/unlike")
    public ApiResponse<?> unlikeBeverage(
            @PathVariable Long beverageId,
            @RequestParam Long memberId) { // TODO: get memberId from security context
        beverageLikeService.unlikeBeverage(memberId, beverageId);
        return ApiResponse.success("ok");
    }
}
