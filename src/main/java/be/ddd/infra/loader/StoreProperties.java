package be.ddd.infra.loader;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.store-info")
@Getter
public class StoreProperties {
    private List<String> defaults = new ArrayList<>();

    public void initializeDefaults(List<String> defaults) {
        this.defaults = defaults;
    }
}
