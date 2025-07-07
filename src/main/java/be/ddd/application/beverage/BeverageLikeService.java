package be.ddd.application.beverage;

import java.util.UUID;

public interface BeverageLikeService {
    void likeBeverage(Long memberId, UUID productId);

    void unlikeBeverage(Long memberId, UUID productId);
}
