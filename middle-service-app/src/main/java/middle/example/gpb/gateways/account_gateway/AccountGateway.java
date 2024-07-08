package middle.example.gpb.gateways.account_gateway;

import middle.example.gpb.models.AccountsListResponseV2;
import middle.example.gpb.models.CreateAccountRequestV2;

import java.util.List;

public interface AccountGateway {

    public void newAccountRegisterResponse(CreateAccountRequestV2 accountRequest, long id);

    List<AccountsListResponseV2> allAccountsResponse(long id);
}
