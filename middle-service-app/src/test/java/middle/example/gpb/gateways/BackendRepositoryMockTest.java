package middle.example.gpb.gateways;

import middle.example.gpb.models.AccountsListResponseV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BackendRepositoryMockTest {

    private BackendRepositoryMock backendRepositoryMock;

    @BeforeEach
    public void setUp() {
        backendRepositoryMock = new BackendRepositoryMock();
    }

    @Test
    public void whenFindAccByNameSuccessTest() {

        var name = "test6";

        long res = backendRepositoryMock.findAccByName(name).get();
        long exp = 6L;

        assertEquals(exp, res);
    }

    @Test
    public void whenFindAccByNameNotFoundTest() {

        var name = "test1234";

        var res = backendRepositoryMock.findAccByName(name);
        var exp = Optional.empty();

        assertEquals(exp, res);
    }

    @Test
    public void whenUpdateAccountListSuccessTest() {

        var testId = 6L;
        var testAccount = new AccountsListResponseV2(
                UUID.fromString("a46e9ea0-917a-4126-9676-8053b8536241"), "upTest", new BigDecimal(6000));

        var notExp = backendRepositoryMock.getRepository().get(testId).get(0);
        backendRepositoryMock.updateAccountList(testId, testAccount);
        var exp = backendRepositoryMock.getRepository().get(testId).get(0);

        assertNotEquals(notExp, testAccount);
        assertEquals(exp, testAccount);
    }

    @Test
    public void whenUpdateAccountListNotFoundAccountTest() {

        var testId = 1L;
        var testAccount = new AccountsListResponseV2(
                UUID.fromString("a46e9ea0-917a-4126-9676-8053b8536241"), "upTest", new BigDecimal(6000));

        var beforeUpdate = backendRepositoryMock.getRepository().get(testId).size();
        backendRepositoryMock.updateAccountList(testId, testAccount);
        var afterUpdate = backendRepositoryMock.getRepository().get(testId).size();

        assertEquals(beforeUpdate, afterUpdate);
    }


}