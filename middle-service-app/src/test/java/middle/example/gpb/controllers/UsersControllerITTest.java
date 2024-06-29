package middle.example.gpb.controllers;

import middle.example.gpb.exeptions.GlobalExceptionHandler;
import middle.example.gpb.gateways.user_gateway.UserGateway;
import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.models.CreateUserRequestV2;
import middle.example.gpb.services.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UsersControllerITTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void whenValidDataAndSuccessCreateNewUserTest() throws Exception {

        String exp = "Пользователь успешно зарегистрирован.";

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 12345, \s
                                  "userName": "test"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value(exp));
    }

    @Test
    public void whenValidDataAndUserAlreadyExistTest() throws Exception {

        String exp = "Такой пользователь уже создан.";

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 1, \s
                                  "userName": "test"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value(exp));
    }

    @Test
    public void whenInvalidDataThenMethodArgumentNotValidExceptionTest() throws Exception {

        String exp = "Полученные данные не валидны, пожалуйста, введите верную информацию.";

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": null, \s
                                  "userName": ""
                                }
                               """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value(Matchers.containsString(exp)));
    }
}