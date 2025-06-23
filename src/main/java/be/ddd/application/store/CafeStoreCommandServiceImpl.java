package be.ddd.application.store;

import be.ddd.domain.entity.crawling.CafeBrand;
import be.ddd.domain.entity.crawling.CafeStore;
import be.ddd.domain.repo.CafeStoreRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CafeStoreCommandServiceImpl implements CafeStoreCommandService {

    private final CafeStoreRepository storeRepository;

    @Override
    public void initDefaultCafeStore(List<String> defaultStores) {
        List<CafeBrand> cafeBrands =
                defaultStores.stream()
                        .map(CafeBrand::findByDisplayName)
                        .flatMap(Optional::stream)
                        .toList();

        if (cafeBrands.isEmpty()) log.warn("기본 Stroe 정보 없음", defaultStores);

        List<CafeStore> existStoreList = storeRepository.findAllByCafeBrandIn(cafeBrands);

        List<CafeStore> toCreateStores =
                cafeBrands.stream()
                        .filter(cafeBrand -> !existStoreList.contains(cafeBrand))
                        .map(cafeBrand -> new CafeStore(cafeBrand, List.of()))
                        .toList();

        if (!toCreateStores.isEmpty()) {
            storeRepository.saveAll(toCreateStores);
        }
    }
}
