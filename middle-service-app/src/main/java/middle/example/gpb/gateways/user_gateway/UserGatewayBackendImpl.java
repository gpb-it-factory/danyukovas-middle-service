package middle.example.gpb.gateways.user_gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import middle.example.gpb.exeptions.CustomBackendServiceRuntimeException;
import middle.example.gpb.models.CreateUserRequestV2;
import middle.example.gpb.models.UserResponseV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "features", name = "backendServiceEnabled", havingValue = "true", matchIfMissing = false)
public class UserGatewayBackendImpl implements UserGateway {

    private final RestClient restClient;
    private final ObjectMapper mapper;

    @Autowired
    public UserGatewayBackendImpl(RestClient restClient, ObjectMapper mapper) {
        this.restClient = restClient;
        this.mapper = mapper;
    }

    @Override
    public void newUserRegisterResponse(CreateUserRequestV2 userRequest) {
        log.info("Запрос в Backend сервис для создания пользователя {}.", userRequest.userId());
        restClient.post()
                .uri("/users")
                .body(userRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    throw new CustomBackendServiceRuntimeException(req.toString(), resp.getBody(), mapper);
                })
                .toBodilessEntity();
    }

    @Override
    public UserResponseV2 getUserResponse(long id) {
        log.info("Запрос в Backend сервис для проверки пользователя {} в системе.", id);
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    throw new CustomBackendServiceRuntimeException(req.toString(), resp.getBody(), mapper);
                })
                .toEntity(UserResponseV2.class)
                .getBody();
    }
}
