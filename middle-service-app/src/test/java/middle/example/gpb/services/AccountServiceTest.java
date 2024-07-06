package middle.example.gpb.services;

import middle.example.gpb.gateways.account_gateway.AccountGateway;
import middle.example.gpb.gateways.user_gateway.UserGateway;
import middle.example.gpb.models.CreateAccountRequestV2;
import middle.example.gpb.models.UserResponseV2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private AccountGateway accountGateway;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void whenCreateAccountFoundUserTest() {

        var testId = 1234;
        var testAcc = new CreateAccountRequestV2("test");
        when(userGateway.getUserResponse(testId)).thenReturn(new UserResponseV2(UUID.randomUUID()));

        accountService.createNewAccount(testAcc, testId);

        verify(accountGateway, times(1)).newAccountRegisterResponse(testAcc, testId);
    }

    @Test
    public void whenCreateAccountNotFoundUserTest() {

        var testId = 5;
        var testAcc = new CreateAccountRequestV2("test");
        when(userGateway.getUserResponse(testId)).thenReturn(null);

        accountService.createNewAccount(testAcc, testId);

        verify(accountGateway, times(0)).newAccountRegisterResponse(testAcc, testId);
    }
}