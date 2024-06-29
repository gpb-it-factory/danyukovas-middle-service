package middle.example.gpb.controllers;

import middle.example.gpb.exeptions.GlobalExceptionHandler;
import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.gateways.account_gateway.AccountGateway;
import middle.example.gpb.gateways.user_gateway.UserGateway;
import middle.example.gpb.models.CreateAccountRequestV2;
import middle.example.gpb.models.CreateUserRequestV2;
import middle.example.gpb.services.AccountService;
import middle.example.gpb.services.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AccountControllerITTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void whenValidDataAndSuccessCreateAccountTest() throws Exception {

        var testId = 1L;
        var newAcc = new CreateAccountRequestV2("test");

        String exp = "Аккаунт успешно создан.";

        mockMvc.perform(post("/api/v2/users/{id}/accounts", testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "accountName": "test"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value(exp));
    }

    @Test
    public void whenValidDataAndExCreateAccountTest() throws Exception {

        var testId = 1234L;
        var newAcc = new CreateAccountRequestV2("test");

        String exp = "Пользователь не найден. Пожалуйста, сначала выполните регистрацию.";

        mockMvc.perform(post("/api/v2/users/{id}/accounts", testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "accountName": "test"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value(exp));
    }
}
