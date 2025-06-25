package be.ddd.application.beverage.dto;

import be.ddd.domain.entity.crawling.BeverageNutrition;
import be.ddd.domain.entity.crawling.BeverageType;
import java.util.UUID;

public record CafeBeverageDetailsDto(
        String name,
        UUID productId,
        String imgUrl,
        BeverageType beverageType,
        BeverageNutrition beverageNutrition,
        CafeStoreDto cafeStoreDto) {

    public static CafeBeverageDetailsDto from(
            String name,
            UUID productId,
            String imgUrl,
            BeverageType beverageType,
            BeverageNutrition beverageNutrition,
            CafeStoreDto cafeStoreDto) {
        return new CafeBeverageDetailsDto(
                name, productId, imgUrl, beverageType, beverageNutrition, cafeStoreDto);
    }
}
