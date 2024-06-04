package middle.example.gpb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "features")
public class FeatureToggleConfig {

    boolean backendServiceEnabled;
}
