package be.ddd.api.dto.res;

import java.util.UUID;

public record BeverageLikeDto(UUID productId, boolean liked, long likeCount) {}
