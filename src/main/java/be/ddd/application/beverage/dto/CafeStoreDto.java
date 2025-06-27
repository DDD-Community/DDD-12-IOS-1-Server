package be.ddd.application.beverage.dto;

import be.ddd.domain.entity.crawling.CafeBrand;
import be.ddd.domain.entity.crawling.CafeStore;

public record CafeStoreDto(CafeBrand cafeBrand) {

    public static CafeStoreDto from(CafeStore cafeStore) {
        return new CafeStoreDto(cafeStore.getCafeBrand());
    }
}
