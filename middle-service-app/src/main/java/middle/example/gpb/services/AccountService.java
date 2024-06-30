package middle.example.gpb.services;

import middle.example.gpb.gateways.account_gateway.AccountGateway;
import middle.example.gpb.gateways.user_gateway.UserGateway;
import middle.example.gpb.models.AccountsListResponseV2;
import middle.example.gpb.models.CreateAccountRequestV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountGateway accountGateway;
    private final UserGateway userGateway;

    @Autowired
    public AccountService(AccountGateway accountGateway, UserGateway userGateway) {
        this.accountGateway = accountGateway;
        this.userGateway = userGateway;
    }

    public void createNewAccount(CreateAccountRequestV2 accountRequest, long id) {
        if (userGateway.getUserResponse(id) != null) {
            accountGateway.newAccountRegisterResponse(accountRequest, id);
        }
    }

    public List<AccountsListResponseV2> getAllAccounts(long id) {
        if (userGateway.getUserResponse(id) != null) {
            return accountGateway.allAccountsResponse(id);
        } else {
            throw new RuntimeException();
        }
    }
}
