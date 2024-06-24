package middle.example.gpb.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    public final int MAX_CON_PER_ROUTE;
    public final int MAX_CON_TOTAL;
    public final int CONNECTION_TIMEOUT_MS;
    public final int REQUEST_TIMEOUT_MS;
    public final String URI;

    public RestClientConfig(@Value("${httpclient.maxConPerRoute}") int maxConPerRoute,
                            @Value("${httpclient.maxConTotal}") int maxConTotal,
                            @Value("${httpclient.connectionTimeoutMs}") int connectionTimeoutMs,
                            @Value("${httpclient.requestTimeoutMs}") int requestTimeoutMs,
                            @Value("${httpclient.back-service-uri}") String uri) {
        this.MAX_CON_PER_ROUTE = maxConPerRoute;
        this.MAX_CON_TOTAL = maxConTotal;
        this.CONNECTION_TIMEOUT_MS = connectionTimeoutMs;
        this.REQUEST_TIMEOUT_MS = requestTimeoutMs;
        this.URI = uri;
    }

    public CloseableHttpClient httpClientBuilder() {
        PoolingHttpClientConnectionManager manager = PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnPerRoute(MAX_CON_PER_ROUTE)
                .setMaxConnTotal(MAX_CON_TOTAL)
                .build();
        return HttpClients.custom()
                .setConnectionManager(manager)
                .build();
    }

    @Bean
    public RestClient restClient() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(CONNECTION_TIMEOUT_MS);
        factory.setConnectionRequestTimeout(REQUEST_TIMEOUT_MS);
        factory.setHttpClient(httpClientBuilder());
        return RestClient.builder().baseUrl(URI).requestFactory(factory).build();
    }
}
