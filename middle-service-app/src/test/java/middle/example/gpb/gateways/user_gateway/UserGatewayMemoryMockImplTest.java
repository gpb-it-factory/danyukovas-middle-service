package middle.example.gpb.gateways.user_gateway;

import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.models.CreateUserRequestV2;
import middle.example.gpb.models.UserResponseV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserGatewayMemoryMockImplTest {

    private UserGatewayMemoryMockImpl gatewayMock;

    @BeforeEach
    public void setUp() {
        BackendRepositoryMock repositoryMock = new BackendRepositoryMock();
        gatewayMock = new UserGatewayMemoryMockImpl(repositoryMock);
    }

    @Test
    public void whenUserNotCreatedTest() {

        var newUser = new CreateUserRequestV2(6L, "test");

        boolean res = gatewayMock.newUserRegisterResponse(newUser);

        assertTrue(res);
    }

    @Test
    public void whenUserAlreadyCreatedTest() {

        var newUser = new CreateUserRequestV2(1L, "test");

        boolean res = gatewayMock.newUserRegisterResponse(newUser);

        assertFalse(res);
    }

    @Test
    public void whenUserResponseFindUserTest() {

        var testId = 1L;

        UserResponseV2 res = gatewayMock.getUserResponse(testId);

        assertNotNull(res);
    }

    @Test
    public void whenUserResponseNotFoundTest() {

        long testId = 6L;

        UserResponseV2 res = gatewayMock.getUserResponse(testId);

        assertNull(res);
    }
}