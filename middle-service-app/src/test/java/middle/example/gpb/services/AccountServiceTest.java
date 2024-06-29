package middle.example.gpb.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.gateways.account_gateway.AccountGatewayMemoryMockImpl;
import middle.example.gpb.gateways.user_gateway.UserGatewayMemoryMockImpl;
import middle.example.gpb.models.CreateAccountRequestV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService accountService;

    @BeforeEach()
    public void setUp() {
        var repMock = new BackendRepositoryMock();
        var userGateway = new UserGatewayMemoryMockImpl(repMock);
        var accountGateway = new AccountGatewayMemoryMockImpl(repMock, new ObjectMapper());
        accountService = new AccountService(accountGateway, userGateway);
    }

    @Test
    public void whenNotFoundUserNest() {

        var testId = 1234;
        var testAcc = new CreateAccountRequestV2("test");

        boolean res = accountService.createNewAccount(testAcc, testId);

        assertFalse(res);
    }

    @Test
    public void whenFindUserTest() {

        var testId = 5;
        var testAcc = new CreateAccountRequestV2("test");

        boolean res = accountService.createNewAccount(testAcc, testId);

        assertTrue(res);
    }
}