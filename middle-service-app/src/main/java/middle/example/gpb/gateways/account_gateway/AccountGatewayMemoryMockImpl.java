package middle.example.gpb.gateways.account_gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import middle.example.gpb.exeptions.CustomBackendServiceRuntimeException;
import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.models.AccountsListResponseV2;
import middle.example.gpb.models.CreateAccountRequestV2;
import middle.example.gpb.models.InnerErrorV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
@ConditionalOnProperty(prefix = "features", name = "backendServiceEnabled", havingValue = "false", matchIfMissing = true)
public class AccountGatewayMemoryMockImpl implements AccountGateway {

    private final BackendRepositoryMock repMock;
    private final ObjectMapper mapper;

    @Autowired
    public AccountGatewayMemoryMockImpl(BackendRepositoryMock repMock, ObjectMapper mapper) {
        this.repMock = repMock;
        this.mapper = mapper;
    }

    @Override
    public void newAccountRegisterResponse(CreateAccountRequestV2 accountRequest, long id) {
        if (repMock.getRepository().get(id).isEmpty()) {
            repMock.getRepository().put(id,
                    Optional.of(new AccountsListResponseV2(UUID.randomUUID(), accountRequest.accountName(), new BigDecimal(5000))));
        } else {
            byte[] error;
            try {
                error = mapper.writeValueAsBytes(new InnerErrorV2("Аккаунт уже создан.", "Error", "409", UUID.randomUUID()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            throw new CustomBackendServiceRuntimeException("message", new ByteArrayInputStream(error), mapper);
        }
    }
}
