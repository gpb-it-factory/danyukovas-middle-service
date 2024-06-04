package middle.example.gpb.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountListResponseTest {

    @Test
    public void convertToJsonTest() throws IOException {
        var data = new AccountListResponse(UUID.fromString("b5424a38-b2de-4a68-91e9-b669aef0cdf9"),
                "Test",new BigDecimal("123245"));
        ObjectMapper mapper = new ObjectMapper();
        String res = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        String exp = Files.readString(Paths.get("src/test/test-data/account-list-response.json"));
        assertEquals(exp, res);
    }

    @Test
    public void convertFromJsonTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String s = Files.readString(Paths.get("src/test/test-data/account-list-response.json"));
        AccountListResponse res = mapper.readValue(s, AccountListResponse.class);
        var exp = new AccountListResponse(UUID.fromString("b5424a38-b2de-4a68-91e9-b669aef0cdf9"),
                "Test", new BigDecimal("123245"));
        assertEquals(exp, res);
    }

}