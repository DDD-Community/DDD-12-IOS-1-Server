package be.ddd.application.store;

import be.ddd.domain.entity.crawling.CafeBrand;
import be.ddd.domain.entity.crawling.CafeStore;
import be.ddd.domain.repo.CafeStoreRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
        log.info(">>> default stores Application : {}", defaultStores);
        List<CafeBrand> cafeBrands =
                defaultStores.stream()
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .map(CafeBrand::findByDisplayName)
                        .flatMap(Optional::stream)
                        .toList();
        log.info(">>> mapped cafeBrands     : {}", cafeBrands);

        if (cafeBrands.isEmpty()) {
            log.warn("기본 Stroe 정보 없음", defaultStores);
            return;
        }

        List<CafeStore> existStoreList = storeRepository.findAllByCafeBrandIn(cafeBrands);

        Set<CafeBrand> existBrands =
                existStoreList.stream().map(CafeStore::getCafeBrand).collect(Collectors.toSet());

        List<CafeStore> toCreateStores =
                cafeBrands.stream()
                        .filter(brand -> !existBrands.contains(brand))
                        .map(CafeStore::of)
                        .toList();

        if (!toCreateStores.isEmpty()) {
            storeRepository.saveAll(toCreateStores);
        }
    }
}
