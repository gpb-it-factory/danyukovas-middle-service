package middle.example.gpb.controllers;

import middle.example.gpb.exeptions.GlobalExceptionHandler;
import middle.example.gpb.gateways.user_gateway.UserGateway;
import middle.example.gpb.gateways.user_gateway.UserRepositoryMock;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UsersControllerITTest {

    @Autowired
    MockMvc mockMvc;
    @SpyBean
    UserService userService;
    @SpyBean
    UserGateway userGateway;
    @SpyBean
    UserRepositoryMock repositoryMock;
    @SpyBean
    GlobalExceptionHandler handler;

    @Test
    public void whenValidDataTest() throws Exception {

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 12, \s
                                  "userName": "test"
                                }
                                """))
                .andExpectAll(
                        status().isOk(),
                        content().string("Пользователь успешно зарегистрирован.")
                );

        verify(userService, times(1)).responseFromBackend(new CreateUserRequestV2(12L, "test"));
        verify(userGateway, times(1)).newUserRegisterResponse(new CreateUserRequestV2(12L, "test"));
        verify(repositoryMock, times(1)).getUsers();
    }

    @Test
    public void whenInvalidDataTest() throws Exception {

            mockMvc.perform(post("/api/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                {
                                  "userId": null, \s
                                  "userName": ""
                                }
                                """))
                    .andExpectAll(
                            status().isOk(),
                            content().string(
                                    Matchers.containsString("Полученные данные не валидны, пожалуйста, введите верную информацию."))
                    );

            verify(handler, times(1)).handleMethodArgumentNotValidException(any(MethodArgumentNotValidException.class));
    }
}