package middle.example.gpb.gateways.account_gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.example.gpb.exeptions.CustomBackendServiceRuntimeException;
import middle.example.gpb.models.CreateAccountRequestV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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
        restClient.post()
                .uri("/{id}/accounts", id)
                .body(accountRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    throw new CustomBackendServiceRuntimeException(req.toString(), resp.getBody(), mapper);
                })
                .toBodilessEntity();
    }
}