package be.ddd.infra.loader;

import be.ddd.application.store.CafeStoreCommandServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class FixedStoreLoader implements ApplicationRunner {

    private final StoreProperties storeProperties;

    private final CafeStoreCommandServiceImpl storeCommandService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(">>> default stores: {}", storeProperties.getDefaults());
        storeCommandService.initDefaultCafeStore(storeProperties.getDefaults());
    }
}
