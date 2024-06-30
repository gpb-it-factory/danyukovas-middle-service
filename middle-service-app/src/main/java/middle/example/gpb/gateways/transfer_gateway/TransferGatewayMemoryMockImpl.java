package middle.example.gpb.gateways.transfer_gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.example.gpb.exeptions.CustomBackendServiceRuntimeException;
import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.gateways.account_gateway.AccountGateway;
import middle.example.gpb.models.AccountsListResponseV2;
import middle.example.gpb.models.CreateTransferRequest;
import middle.example.gpb.models.InnerErrorV2;
import middle.example.gpb.models.TransferResponseV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@ConditionalOnProperty(prefix = "features", name = "backendServiceEnabled", havingValue = "false", matchIfMissing = true)
public class TransferGatewayMemoryMockImpl implements TransferGateway {

    private final BackendRepositoryMock repositoryMock;
    private final ObjectMapper mapper;
    private final AccountGateway accountGateway;
    private final Lock lock = new ReentrantLock();

    @Autowired
    public TransferGatewayMemoryMockImpl(BackendRepositoryMock repositoryMock, ObjectMapper mapper, AccountGateway accountGateway) {
        this.repositoryMock = repositoryMock;
        this.mapper = mapper;
        this.accountGateway = accountGateway;
    }

    @Override
    public TransferResponseV2 postTransferResponse(CreateTransferRequest transferRequest) {
        var findId = repositoryMock.findAccByName(transferRequest.to());
        if (findId.isPresent()) {
            var receiverToId = findId.get();
            lock.lock();
            try {
                var accountFrom = accountGateway.allAccountsResponse(Long.parseLong(transferRequest.from())).get(0);
                var accountTo = accountGateway.allAccountsResponse(receiverToId).get(0);
                BigDecimal transferAmount = transferRequest.amount();
                var newAmountFrom = accountFrom.amount().subtract(transferAmount);
                var newAmountTo = accountTo.amount().add(transferAmount);
                repositoryMock.updateAccountList(Long.parseLong(transferRequest.from()),
                        new AccountsListResponseV2(accountFrom.accountId(),
                                accountFrom.accountName(),
                                newAmountFrom));
                repositoryMock.updateAccountList(receiverToId,
                        new AccountsListResponseV2(accountTo.accountId(),
                                accountTo.accountName(),
                                newAmountTo));
            } finally {
                lock.unlock();
            }
            return new TransferResponseV2(UUID.randomUUID());
        } else {
            try {
                byte[] error = mapper.writeValueAsBytes(
                        new InnerErrorV2("Нет созданных аккаунтов.", "Error", "404", UUID.randomUUID()));
                throw new CustomBackendServiceRuntimeException("message", new ByteArrayInputStream(error), mapper);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
