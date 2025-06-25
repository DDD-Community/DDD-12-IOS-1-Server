package be.ddd.application.beverage;

import be.ddd.application.beverage.dto.CafeBeveragePageDto;
import be.ddd.common.dto.CursorPageResponse;
import be.ddd.common.util.StringBase64EncodingUtil;
import be.ddd.domain.entity.crawling.CafeBeverage;
import be.ddd.domain.repo.CafeBeverageRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeBeverageQueryServiceImpl implements CafeBeverageQueryService {
    private final CafeBeverageRepository beverageRepository;
    private final StringBase64EncodingUtil encodingUtil;

    @Override
    public CursorPageResponse<CafeBeveragePageDto> getCafeBeverageCursorPage(
            Long cursor, int size) {
        Long dynamicCursor = Optional.ofNullable(cursor).orElse(0L);

        PageRequest pageReq = PageRequest.of(0, size + 1, Sort.by("id").ascending());
        List<CafeBeverage> fetch =
                beverageRepository.findByIdGreaterThanOrderByIdAsc(dynamicCursor, pageReq);

        boolean hasNext = fetch.size() > size;

        List<CafeBeveragePageDto> pageResults =
                fetch.stream().limit(size).map(CafeBeveragePageDto::from).toList();

        Long nextCursor = hasNext ? pageResults.get(pageResults.size() - 1).id() : null;

        assert nextCursor != null;
        String encodedNextCursor = null;
        if (hasNext) {
            encodedNextCursor = encodingUtil.encodeSignedCursor(nextCursor);
        }

        return new CursorPageResponse<>(pageResults, encodedNextCursor, hasNext);
    }
}
