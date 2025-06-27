package be.ddd.application.batch.dto;

public record BeverageNutritionDto(
        Integer servingKcal,
        Double saturatedFatG,
        Double proteinG,
        Integer sodiumMg,
        Integer sugarG,
        Integer caffeineMg) {}
