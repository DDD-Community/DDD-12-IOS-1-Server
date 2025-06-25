package be.ddd.application.beverage;

import be.ddd.application.beverage.dto.CafeBeveragePageDto;
import be.ddd.common.dto.CursorPageResponse;
import be.ddd.common.util.StringBase64EncodingUtil;
import be.ddd.domain.entity.crawling.CafeBeverage;
import be.ddd.domain.repo.CafeBeverageRepository;
import java.util.List;
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

        PageRequest pageReq = PageRequest.of(0, size + 1, Sort.by("id").ascending());
        List<CafeBeverage> fetch =
                beverageRepository.findByIdGreaterThanOrderByIdAsc(cursor, pageReq);

        boolean hasNext = fetch.size() > size;

        List<CafeBeveragePageDto> pageResults =
                fetch.stream().limit(size).map(CafeBeveragePageDto::from).toList();

        String nextCursor = null;
        if (hasNext) {
            long lastId = fetch.get(size - 1).getId();
            nextCursor = encodingUtil.encodeSignedCursor(lastId);
        }

        return new CursorPageResponse<>(pageResults, nextCursor, hasNext);
    }
}
