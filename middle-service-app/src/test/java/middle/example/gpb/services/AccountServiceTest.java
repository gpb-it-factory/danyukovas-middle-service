package middle.example.gpb.services;

import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.gateways.account_gateway.AccountGatewayMemoryMockImpl;
import middle.example.gpb.gateways.user_gateway.UserGatewayMemoryMockImpl;
import middle.example.gpb.models.CreateAccountRequestV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountServiceTest {

    private AccountService accountService;

    @BeforeEach()
    public void setUp() {
        var repMock = new BackendRepositoryMock();
        var userGateway = new UserGatewayMemoryMockImpl(repMock);
        var accountGateway = new AccountGatewayMemoryMockImpl(repMock);
        accountService = new AccountService(accountGateway, userGateway);
    }

    @Test
    public void whenNotFoundUserNest() {

        var testId = 6;
        var testAcc = new CreateAccountRequestV2("test");

        String res = accountService.createNewAccount(testAcc, testId);
        String exp = "Пользователь не найден. Пожалуйста, сначала выполните регистрацию.";

        assertEquals(exp, res);
    }

    @Test
    public void whenFindUserTest() {

        var testId = 5;
        var testAcc = new CreateAccountRequestV2("test");

        String res = accountService.createNewAccount(testAcc, testId);
        String exp = "Аккаунт успешно создан.";

        assertEquals(exp, res);
    }

    @Test
    public void whenGetAllAccountsNotFoundUserTest() {

        var testId = 6;

        String res = accountService.getAllAccounts(testId);
        String exp =  "Пользователь не найден. Пожалуйста, сначала выполните регистрацию.";

        assertEquals(exp, res);
    }

    @Test
    public void whenGetAllAccountsFoundUserZeroAccountsTest() {

        var testId = 1;

        String res = accountService.getAllAccounts(testId);
        String exp =  "Нет ни одного созданного аккаунта.";

        assertEquals(exp, res);
    }

    @Test
    public void whenGetAllAccountsFoundUserHasAccountsTest() {

        var testId = 66;

        String res = accountService.getAllAccounts(testId);
        String exp = """
                Название аккаунта: test1
                Сумма счета: 5000.00
                Название аккаунта: test2
                Сумма счета: 6000.00""";

        assertEquals(exp, res);
    }
}