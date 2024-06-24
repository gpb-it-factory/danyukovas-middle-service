package middle.example.gpb.gateways.account_gateway;

import middle.example.gpb.models.CreateAccountRequestV2;
import middle.example.gpb.models.CreateUserRequestV2;

public interface AccountGateway {

    public void newAccountRegisterResponse(CreateAccountRequestV2 accountRequest, long id);
}
