package middle.example.gpb.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "httpclient")
public class RestClientConfig {

    private String uri;
    private int maxConPerRoute;
    private int maxConTotal;
    private int connectionTimeoutMs;
    private int requestTimeoutMs;

    public CloseableHttpClient httpClientBuilder() {
        PoolingHttpClientConnectionManager manager = PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnPerRoute(maxConPerRoute)
                .setMaxConnTotal(maxConTotal)
                .build();
        return HttpClients.custom()
                .setConnectionManager(manager)
                .build();
    }

    @Bean
    public RestClient restClient() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(connectionTimeoutMs);
        factory.setConnectionRequestTimeout(requestTimeoutMs);
        factory.setHttpClient(httpClientBuilder());
        return RestClient.builder().baseUrl(uri).requestFactory(factory).build();
    }
}
