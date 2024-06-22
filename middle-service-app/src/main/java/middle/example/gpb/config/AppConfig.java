package middle.example.gpb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:http-client-config.yml")
public class AppConfig {}
