package middle.example.gpb.gateways.account_gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.example.gpb.exeptions.CustomBackendServiceRuntimeException;
import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.models.AccountsListResponseV2;
import middle.example.gpb.models.CreateAccountRequestV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountGatewayMemoryMockImplTest {

    private AccountGatewayMemoryMockImpl gatewayMock;
    private BackendRepositoryMock repositoryMock;

    @BeforeEach
    public void setUp() {
        ObjectMapper mapper = new ObjectMapper();
        this.repositoryMock = new BackendRepositoryMock();
        this.gatewayMock = new AccountGatewayMemoryMockImpl(repositoryMock, mapper);
    }

    @Test
    public void whenPutNewAccountTest() {

        var newAcc = new CreateAccountRequestV2("test");
        long testId = 5;

        gatewayMock.newAccountRegisterResponse(newAcc, testId);
        var res = repositoryMock.getRepository().get(testId);

        assertNotNull(res);
    }

    @Test
    public void whenAccountAlreadyExistTest() {

        var newAcc = new CreateAccountRequestV2("test");
        long testId = 6;

        assertThrows(CustomBackendServiceRuntimeException.class,
                () -> gatewayMock.newAccountRegisterResponse(newAcc, testId));
    }

    @Test
    public void whenUserNotRegisteredTest() {

        var newAcc = new CreateAccountRequestV2("test");
        long testId = 66L;

        assertThrows(NullPointerException.class, () -> {
            gatewayMock.newAccountRegisterResponse(newAcc, testId);
        });
    }

    @Test
    public void whenGetAllAccountsSuccessTest() {

        var testId = 6L;

        var res = gatewayMock.allAccountsResponse(testId);
        var exp = List.of(
                new AccountsListResponseV2(UUID.fromString("a46e9ea0-917a-4126-9676-8053b8536241"), "test", new BigDecimal(5000)));

        assertEquals(exp, res);
    }

    @Test
    public void whenGetAllAccountsNotFoundAccountsTest() {

        var testId = 5L;

        assertThrows(CustomBackendServiceRuntimeException.class,
                () -> gatewayMock.allAccountsResponse(testId));
    }
}