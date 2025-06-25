package be.ddd.common.dto;

import java.util.List;

public record CursorPageResponse<T>(List<T> items, String nextCursor, boolean hasNext) {}
