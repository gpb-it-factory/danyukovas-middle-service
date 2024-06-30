package middle.example.gpb.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

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
    public void whenValidDataAndAccountAlreadyExistTest() throws Exception {

        var testId = 6L;

        String exp = "Аккаунт уже существует.";

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
    public void whenValidDataAndUserNotRegisteredAndTryCreateAccountTest() throws Exception {

        var testId = 1234L;

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
