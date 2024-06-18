package middle.example.gpb.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "features")
public class FeatureToggleConfig {

    private final boolean backendServiceEnabled;

    public FeatureToggleConfig(@Value("${backendServiceEnabled}") boolean backendServiceEnabled) {
        this.backendServiceEnabled = backendServiceEnabled;
    }
}
