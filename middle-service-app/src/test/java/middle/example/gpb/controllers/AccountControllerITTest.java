package middle.example.gpb.controllers;

import middle.example.gpb.exeptions.GlobalExceptionHandler;
import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.gateways.account_gateway.AccountGateway;
import middle.example.gpb.gateways.user_gateway.UserGateway;
import middle.example.gpb.models.CreateAccountRequestV2;
import middle.example.gpb.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AccountControllerITTest {

    @Autowired
    MockMvc mockMvc;
    @SpyBean
    AccountService accountService;
    @SpyBean
    UserGateway userGateway;
    @SpyBean
    AccountGateway accountGateway;
    @SpyBean
    BackendRepositoryMock repositoryMock;
    @SpyBean
    GlobalExceptionHandler handler;

    @Test
    public void whenValidDataTest() throws Exception {

        var testId = 1L;
        var newAcc = new CreateAccountRequestV2("test");

        mockMvc.perform(post("/api/v2/users/1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "accountName": "test"
                                }
                                """))
                .andExpectAll(
                        status().isOk(),
                        content().string("Аккаунт успешно создан.")
                );

        verify(accountService, times(1)).createNewAccount(newAcc, testId);
        verify(userGateway, times(1)).getUserResponse(testId);
        verify(accountGateway, times(1)).newAccountRegisterResponse(newAcc, testId);
        verify(repositoryMock, times(2)).getRepository();
    }

    @Test
    public void getAccountBalancePerformTest() throws Exception {

        var testId = 66L;

        mockMvc.perform(get("/api/v2/users/66/accounts"))
                .andExpectAll(
                        status().isOk(),
                        content().string("""
                Название аккаунта: test1
                Сумма счета: 5000.00
                Название аккаунта: test2
                Сумма счета: 6000.00""")
                );

        verify(accountService, times(1)).getAllAccounts(testId);
        verify(userGateway, times(1)).getUserResponse(testId);
        verify(accountGateway, times(1)).allAccountsResponse(testId);
        verify(repositoryMock, times(2)).getRepository();
    }
}
