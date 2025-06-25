package be.ddd.application.beverage.dto;

import be.ddd.domain.entity.crawling.BeverageType;
import be.ddd.domain.entity.crawling.CafeBeverage;

public record CafeBeveragePageDto(
        Long id, String name, String imgUrl, BeverageType beverageType, CafeStoreDto cafeStoreDto) {

    public static CafeBeveragePageDto from(CafeBeverage e) {
        return new CafeBeveragePageDto(
                e.getId(),
                e.getName(),
                e.getImgUrl(),
                e.getBeverageType(),
                new CafeStoreDto(e.getCafeStore().getCafeBrand()));
    }
}
