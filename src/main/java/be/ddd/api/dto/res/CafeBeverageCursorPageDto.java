package be.ddd.api.dto.res;

import java.util.List;

public record CafeBeverageCursorPageDto<T>(
        List<T> items, String nextCursor, boolean hasNext, long likeCount) {}
