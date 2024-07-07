package middle.example.gpb.gateways.account_gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import middle.example.gpb.exeptions.CustomBackendServiceRuntimeException;
import middle.example.gpb.models.AccountsListResponseV2;
import middle.example.gpb.models.CreateAccountRequestV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "features", name = "backendServiceEnabled", havingValue = "true", matchIfMissing = false)
public class AccountGatewayBackendImpl implements AccountGateway {

    private  final RestClient restClient;
    private final ObjectMapper mapper;

    @Autowired
    public AccountGatewayBackendImpl(RestClient restClient, ObjectMapper mapper) {
        this.restClient = restClient;
        this.mapper = mapper;
    }

    @Override
    public void newAccountRegisterResponse(CreateAccountRequestV2 accountRequest, long id) {
        log.info("Запрос в Backend сервис для создания аккаунта пользователю {}.", id);
        restClient.post()
                .uri("/users/{id}/accounts", id)
                .body(accountRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    throw new CustomBackendServiceRuntimeException(req.toString(), resp.getBody(), mapper);
                })
                .toBodilessEntity();
    }
  
    @Override
    public List<AccountsListResponseV2> allAccountsResponse(long id) {
        log.info("Запрос в Backend сервис для получения аккаунтов пользователя {}.", id);
        return restClient.get()
                .uri("users/{id}/accounts", id)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    throw new CustomBackendServiceRuntimeException(req.toString(), resp.getBody(), mapper);
                })
                .toEntity(new ParameterizedTypeReference<List<AccountsListResponseV2>>() {})
                .getBody();
    }
}
