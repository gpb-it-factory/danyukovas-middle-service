package middle.example.gpb.gateways.transfer_gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.example.gpb.exeptions.CustomBackendServiceRuntimeException;
import middle.example.gpb.models.CreateTransferRequest;
import middle.example.gpb.models.TransferResponseV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@ConditionalOnProperty(prefix = "features", name = "backendServiceEnabled", havingValue = "true", matchIfMissing = false)
public class TransferGatewayBackendImpl implements TransferGateway {

    private final RestClient restClient;
    private final ObjectMapper mapper;

    @Autowired
    public TransferGatewayBackendImpl(RestClient restClient, ObjectMapper mapper) {
        this.restClient = restClient;
        this.mapper = mapper;
    }

    @Override
    public TransferResponseV2 postTransferResponse(CreateTransferRequest transferRequest) {
        return restClient.post()
                .uri("/transfers")
                .body(transferRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    throw new CustomBackendServiceRuntimeException(req.toString(), resp.getBody(), mapper);
                })
                .toEntity(TransferResponseV2.class)
                .getBody();
    }
}
