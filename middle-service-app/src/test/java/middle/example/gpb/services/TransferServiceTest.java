package middle.example.gpb.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.gateways.account_gateway.AccountGateway;
import middle.example.gpb.gateways.account_gateway.AccountGatewayMemoryMockImpl;
import middle.example.gpb.gateways.transfer_gateway.TransferGateway;
import middle.example.gpb.gateways.transfer_gateway.TransferGatewayMemoryMockImpl;
import middle.example.gpb.gateways.user_gateway.UserGateway;
import middle.example.gpb.gateways.user_gateway.UserGatewayMemoryMockImpl;
import middle.example.gpb.models.CreateTransferRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {

    private TransferService transferService;

    @BeforeEach
    public void setUp() {
        ObjectMapper mapper = new ObjectMapper();
        BackendRepositoryMock repositoryMock = new BackendRepositoryMock();
        AccountGateway accountGateway = new AccountGatewayMemoryMockImpl(repositoryMock, mapper);
        UserGateway userGateway = new UserGatewayMemoryMockImpl(repositoryMock, mapper);
        TransferGateway transferGateway = new TransferGatewayMemoryMockImpl(repositoryMock, mapper, accountGateway);
        transferService = new TransferService(userGateway, accountGateway, transferGateway);
    }

    @Test
    public void whenTransferMoneyNotEnoughBalanceForTransactionTest() {

        var testTransferReq = new CreateTransferRequest("6", "test7", new BigDecimal(10000));

        boolean res = transferService.transferMoney(testTransferReq);

        assertFalse(res);
    }

    @Test
    public void whenTransferMoneyEnoughBalanceForTransactionTest() {

        var testTransferReq = new CreateTransferRequest("6", "test7", new BigDecimal(3000));

        boolean res = transferService.transferMoney(testTransferReq);

        assertTrue(res);
    }

}