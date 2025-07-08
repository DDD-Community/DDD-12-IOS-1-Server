package be.ddd.application.beverage;

import be.ddd.api.dto.res.BeverageLikeDto;
import java.util.UUID;

public interface BeverageLikeService {
    BeverageLikeDto likeBeverage(Long memberId, UUID productId);

    BeverageLikeDto unlikeBeverage(Long memberId, UUID productId);
}
