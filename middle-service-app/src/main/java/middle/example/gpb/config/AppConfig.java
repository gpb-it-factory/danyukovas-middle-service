package middle.example.gpb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-toggle.yml")
public class AppConfig {}
