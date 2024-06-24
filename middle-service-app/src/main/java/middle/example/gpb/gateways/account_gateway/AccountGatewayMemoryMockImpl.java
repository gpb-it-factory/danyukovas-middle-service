package middle.example.gpb.gateways.account_gateway;

import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.models.CreateAccountRequestV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
@ConditionalOnProperty(prefix = "features", name = "backendServiceEnabled", havingValue = "false", matchIfMissing = true)
public class AccountGatewayMemoryMockImpl implements AccountGateway {

    private final BackendRepositoryMock repMock;

    @Autowired
    public AccountGatewayMemoryMockImpl(BackendRepositoryMock repMock) {
        this.repMock = repMock;
    }

    @Override
    public void newAccountRegisterResponse(CreateAccountRequestV2 accountRequest, long id) {
        Map<String, BigDecimal> res = repMock.getRepository().get(id);
            res.put(accountRequest.accountName(), new BigDecimal(5000));
    }
}
