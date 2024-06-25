package middle.example.gpb.gateways.account_gateway;

import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.models.AccountsListResponseV2;
import middle.example.gpb.models.CreateAccountRequestV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        List<AccountsListResponseV2> res = repMock.getRepository().get(id);
            res.add(new AccountsListResponseV2(UUID.randomUUID(), accountRequest.accountName(), new BigDecimal(5000)));
    }
}
