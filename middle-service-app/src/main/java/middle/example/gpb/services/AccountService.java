package middle.example.gpb.services;

import middle.example.gpb.gateways.account_gateway.AccountGateway;
import middle.example.gpb.gateways.user_gateway.UserGateway;
import middle.example.gpb.models.CreateAccountRequestV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountGateway accountGateway;
    private final UserGateway userGateway;

    @Autowired
    public AccountService(AccountGateway accountGateway, UserGateway userGateway) {
        this.accountGateway = accountGateway;
        this.userGateway = userGateway;
    }

    public String createNewAccount(CreateAccountRequestV2 accountRequest, long id) {
        if (userGateway.getUserResponse(id) != null) {
            accountGateway.newAccountRegisterResponse(accountRequest, id);
            return "Аккаунт успешно создан.";
        } else {
            return "Пользователь не найден. Пожалуйста, сначала выполните регистрацию.";
        }
    }
}
