package middle.example.gpb.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountsListResponseV2Test {

    @Test
    public void convertToJsonTest() throws IOException {

        var data = new AccountsListResponseV2(UUID.fromString("b5424a38-b2de-4a68-91e9-b669aef0cdf9"),
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

        AccountsListResponseV2 res = mapper.readValue(s, AccountsListResponseV2.class);
        var exp = new AccountsListResponseV2(UUID.fromString("b5424a38-b2de-4a68-91e9-b669aef0cdf9"),

                "Test", new BigDecimal("123245"));
        assertEquals(exp, res);
    }

    @Test
    public void convertJsonToArray() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String s = Files.readString(Paths.get("src/test/test-data/account-response-array.json"));

        List<AccountsListResponseV2> res = mapper.readValue(s, new TypeReference<List<AccountsListResponseV2>>(){});
        var exp = List.of(new AccountsListResponseV2(UUID.fromString("b5424a38-b2de-4a68-91e9-b669aef0cdf9"),
                "Test1", new BigDecimal("123245")),
                new AccountsListResponseV2(UUID.fromString("b6424a38-b2de-4a68-91e9-b669aef0cdf9"),
                "Test2", new BigDecimal("232456")));

        assertArrayEquals(exp.toArray(), res.toArray());
    }

}