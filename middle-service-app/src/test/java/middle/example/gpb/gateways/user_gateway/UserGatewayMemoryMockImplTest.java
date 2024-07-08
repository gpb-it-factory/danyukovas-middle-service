package middle.example.gpb.gateways.user_gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.example.gpb.exeptions.CustomBackendServiceRuntimeException;
import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.models.CreateUserRequestV2;
import middle.example.gpb.models.UserResponseV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserGatewayMemoryMockImplTest {

    private UserGatewayMemoryMockImpl userGatewayMock;
    private BackendRepositoryMock repositoryMock;

    @BeforeEach
    public void setUp() {
        repositoryMock = new BackendRepositoryMock();
        userGatewayMock = new UserGatewayMemoryMockImpl(repositoryMock, new ObjectMapper());
    }

    @Test
    public void whenUserNotCreatedTest() {

        var newUser = new CreateUserRequestV2(10L, "test");
        var sizeUntilOperation = repositoryMock.getRepository().size();

        userGatewayMock.newUserRegisterResponse(newUser);
        var finSizeRep = repositoryMock.getRepository().size();
        var finSizeUsers = repositoryMock.getUsers().size();

        assertEquals(sizeUntilOperation + 1, finSizeRep);
        assertEquals(sizeUntilOperation + 1, finSizeUsers);
    }

    @Test
    public void whenUserAlreadyCreatedTest() {

        var newUser = new CreateUserRequestV2(1L, "test");

        assertThrows(CustomBackendServiceRuntimeException.class,
                () -> userGatewayMock.newUserRegisterResponse(newUser));
    }

    @Test
    public void whenUserResponseFondUserTest() {

        var testId = 1L;

        UserResponseV2 res = userGatewayMock.getUserResponse(testId);

        assertNotNull(res);
    }

    @Test
    public void whenUserResponseNotFoundTest() {

        long testId = 66L;

        assertThrows(CustomBackendServiceRuntimeException.class,
                () -> userGatewayMock.getUserResponse(testId));
    }
}