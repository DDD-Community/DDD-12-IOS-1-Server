package be.ddd.application.batch.dto;

import be.ddd.domain.entity.crawling.BeverageNutrition;
import be.ddd.domain.entity.crawling.BeverageType;
import be.ddd.domain.entity.crawling.CafeBeverage;
import be.ddd.domain.entity.crawling.CafeStore;
import java.util.Optional;
import java.util.UUID;

public record LambdaBeverageDto(
        String name, String image, String beverageType, BeverageNutritionDto beverageNutrition) {

    public CafeBeverage toEntity(CafeStore cafeStore) {
        BeverageNutrition nutrition =
                Optional.ofNullable(beverageNutrition)
                        .map(BeverageNutrition::from)
                        .orElseGet(BeverageNutrition::empty);
        System.out.println("BeverageNutrition: " + nutrition);
        System.out.println("dto:" + beverageNutrition);
        BeverageType type =
                Optional.ofNullable(beverageType)
                        .map(String::toUpperCase)
                        .map(BeverageType::valueOf)
                        .orElse(null);

        return CafeBeverage.of(name, UUID.randomUUID(), cafeStore, image, nutrition, type);
    }
}
