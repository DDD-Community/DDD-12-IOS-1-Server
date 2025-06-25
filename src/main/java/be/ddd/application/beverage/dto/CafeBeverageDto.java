package be.ddd.application.beverage.dto;

import be.ddd.domain.entity.crawling.BeverageNutrition;
import be.ddd.domain.entity.crawling.BeverageType;
import be.ddd.domain.entity.crawling.CafeBeverage;

public record CafeBeverageDto(
        Long id,
        String name,
        String imgUrl,
        BeverageNutrition beverageNutrition,
        BeverageType beverageType,
        CafeStoreDto cafeStoreDto) {

    public static CafeBeverageDto from(CafeBeverage e) {
        return new CafeBeverageDto(
                e.getId(),
                e.getName(),
                e.getImgUrl(),
                e.getBeverageNutrition(),
                e.getBeverageType(),
                new CafeStoreDto(e.getCafeStore().getCafeBrand()));
    }
}
