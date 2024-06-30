package middle.example.gpb.services;

import middle.example.gpb.gateways.account_gateway.AccountGateway;
import middle.example.gpb.gateways.transfer_gateway.TransferGateway;
import middle.example.gpb.gateways.user_gateway.UserGateway;
import middle.example.gpb.models.CreateTransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {

    private final UserGateway userGateway;
    private final AccountGateway accountGateway;
    private final TransferGateway transferGateway;

    @Autowired
    public TransferService(UserGateway userGateway, AccountGateway accountGateway, TransferGateway transferGateway) {
        this.userGateway = userGateway;
        this.accountGateway = accountGateway;
        this.transferGateway = transferGateway;
    }

    public boolean transferMoney(CreateTransferRequest transferRequest) {
        long id = Long.parseLong(transferRequest.from());
        if (userGateway.getUserResponse(id) == null) {
            throw new RuntimeException();
        }
        var account = accountGateway.allAccountsResponse(id).get(0);
        var newAmount = account.amount().subtract(transferRequest.amount());
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        if (transferGateway.postTransferResponse(transferRequest) == null) {
            throw new RuntimeException();
        }
        return true;
    }
}

