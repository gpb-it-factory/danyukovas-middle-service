package middle.example.gpb.gateways.account_gateway;

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
        this.repositoryMock = new BackendRepositoryMock();
        this.gatewayMock = new AccountGatewayMemoryMockImpl(repositoryMock);
    }

    @Test
    public void whenPutNewAccountTest() {

        var newAcc = new CreateAccountRequestV2("test");
        long testId = 5;

        gatewayMock.newAccountRegisterResponse(newAcc, testId);
        int res = repositoryMock.getRepository().get(testId).size();

        assertThat(res).isEqualTo(1);
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