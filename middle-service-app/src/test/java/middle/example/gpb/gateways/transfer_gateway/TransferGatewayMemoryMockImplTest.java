package middle.example.gpb.gateways.transfer_gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.example.gpb.exeptions.CustomBackendServiceRuntimeException;
import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.gateways.account_gateway.AccountGatewayMemoryMockImpl;
import middle.example.gpb.models.CreateTransferRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransferGatewayMemoryMockImplTest {

    private TransferGatewayMemoryMockImpl transferGateway;
    private BackendRepositoryMock repMock;

    @BeforeEach
    public void setUp() {
        repMock = new BackendRepositoryMock();
        var mapper = new ObjectMapper();
        var accountGateway = new AccountGatewayMemoryMockImpl(repMock, mapper);
        transferGateway = new TransferGatewayMemoryMockImpl(repMock, mapper, accountGateway);
    }

    @Test
    public void whenPostTransferResponseFindAccountAndSuccessTransferTest() {

        var testTransferRequest = new CreateTransferRequest("6", "test7", new BigDecimal(4000));

        var res = transferGateway.postTransferResponse(testTransferRequest);
        var res2 = repMock.getRepository().get(6L).get(0).amount();
        var exp2 = new BigDecimal(1000);
        var res3 = repMock.getRepository().get(7L).get(0).amount();
        var exp3 = new BigDecimal(5000);

        assertNotNull(res);
        assertEquals(exp2, res2);
        assertEquals(exp3, res3);
    }

    @Test
    public void whenPostTransferResponseNotFondAccountTest() {

        var testTransferRequest = new CreateTransferRequest("6", "test1234", new BigDecimal(4000));

        var res2 = repMock.getRepository().get(6L).get(0).amount();
        var exp2 = new BigDecimal(5000);
        var res3 = repMock.getRepository().get(7L).get(0).amount();
        var exp3 = new BigDecimal(1000);

        assertThrows(CustomBackendServiceRuntimeException.class,
                () -> transferGateway.postTransferResponse(testTransferRequest));
        assertEquals(exp2, res2);
        assertEquals(exp3, res3);
    }

}