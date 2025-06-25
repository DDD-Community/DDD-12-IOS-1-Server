package be.ddd.domain.entity.crawling;

import be.ddd.application.batch.dto.LambdaBeverageDto;
import be.ddd.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cafe_beverages")
public class CafeBeverage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_beverage_id")
    private Long id;

    @Column(name = "product_id", nullable = false, unique = true)
    private UUID productId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cafe_store_id", nullable = false)
    private CafeStore cafeStore;

    private String imgUrl;

    private BeverageNutrition beverageNutrition;

    @Enumerated(EnumType.STRING)
    private BeverageType beverageType;

    public void updateFromDto(LambdaBeverageDto dto) {
        if (dto.image() != null) {
            this.imgUrl = dto.image();
        }

        if (dto.beverageType() != null) {
            this.beverageType =
                    BeverageType.valueOf(dto.beverageType().toUpperCase().replace(" ", "_"));
        }

        if (dto.beverageNutrition() != null) {
            this.beverageNutrition = BeverageNutrition.from(dto.beverageNutrition());
        }
    }

    public static CafeBeverage of(
            String name,
            UUID productId,
            CafeStore cafeStore,
            String imgUrl,
            BeverageNutrition beverageNutrition,
            BeverageType beverageType) {
        return new CafeBeverage(
                null, name, productId, cafeStore, imgUrl, beverageNutrition, beverageType);
    }

    public CafeBeverage(
            Long id,
            String name,
            UUID productId,
            CafeStore cafeStore,
            String imgUrl,
            BeverageNutrition beverageNutrition,
            BeverageType beverageType) {
        this.id = id;
        this.name = name;
        this.productId = productId;
        this.cafeStore = cafeStore;
        this.imgUrl = imgUrl;
        this.beverageNutrition = beverageNutrition;
        this.beverageType = beverageType;
    }
}
