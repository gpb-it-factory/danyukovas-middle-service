package middle.example.gpb.gateways.account_gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.models.CreateAccountRequestV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class AccountGatewayMemoryMockImplTest {

    private AccountGatewayMemoryMockImpl gatewayMock;
    private BackendRepositoryMock repositoryMock;

    @BeforeEach
    public void setUp() {
        ObjectMapper mapper = new ObjectMapper();
        this.repositoryMock = new BackendRepositoryMock();
        this.gatewayMock = new AccountGatewayMemoryMockImpl(repositoryMock, mapper);
    }

    @Test
    public void whenPutNewAccountTest() {

        var newAcc = new CreateAccountRequestV2("test");
        long testId = 5;

        gatewayMock.newAccountRegisterResponse(newAcc, testId);
        var res = repositoryMock.getRepository().get(testId);

        assertNotNull(res);
    }

    @Test
    public void whenNotPutNewAccountTest() {

        var newAcc = new CreateAccountRequestV2("test");
        long testId = 6;

        assertThrows(NullPointerException.class, () -> {
            gatewayMock.newAccountRegisterResponse(newAcc, testId);
        });
    }

}