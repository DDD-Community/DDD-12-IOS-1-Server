package be.ddd.application.beverage.dto;

import be.ddd.domain.entity.crawling.BeverageType;
import be.ddd.domain.entity.crawling.CafeBeverage;
import java.util.UUID;

public record CafeBeveragePageDto(
        UUID productId,
        String name,
        String imgUrl,
        BeverageType beverageType,
        CafeStoreDto cafeStoreDto) {

    public static CafeBeveragePageDto from(CafeBeverage e) {
        return new CafeBeveragePageDto(
                e.getProductId(),
                e.getName(),
                e.getImgUrl(),
                e.getBeverageType(),
                new CafeStoreDto(e.getCafeStore().getCafeBrand()));
    }
}
