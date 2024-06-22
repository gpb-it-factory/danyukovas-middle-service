package middle.example.gpb.gateways.user_gateway;

import middle.example.gpb.models.CreateUserRequestV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserGatewayMemoryMockImplTest {

    private UserGatewayMemoryMockImpl gatewayMock;

    @BeforeEach
    public void setUp() {
        UserRepositoryMock repositoryMock = new UserRepositoryMock();
        gatewayMock = new UserGatewayMemoryMockImpl(repositoryMock);
    }

    @Test
    public void whenUserNotCreatedTest() {

        var newUser = new CreateUserRequestV2(6L, "test");

        String res = gatewayMock.newUserRegisterResponse(newUser);
        String exp = "Пользователь успешно зарегистрирован.";

        assertEquals(exp, res);
    }

    @Test
    public void whenUserAlreadyCreatedTest() {

        var newUser = new CreateUserRequestV2(1L, "test");

        String res = gatewayMock.newUserRegisterResponse(newUser);
        String exp = "Такой пользователь уже создан.";

        assertEquals(exp, res);
    }
}