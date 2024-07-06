package middle.example.gpb.gateways;

import lombok.Getter;
import middle.example.gpb.models.AccountsListResponseV2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class BackendRepositoryMock {

    private final Map<Long, List<AccountsListResponseV2>> repository = new ConcurrentHashMap<>(Map.of(
            1L, new ArrayList<>(),
            2L, new ArrayList<>(),
            3L, new ArrayList<>(),
            4L, new ArrayList<>(),
            5L, new ArrayList<>(),
            6L, new ArrayList<>(List.of(
                    new AccountsListResponseV2(UUID.fromString("a46e9ea0-917a-4126-9676-8053b8536241"), "test", new BigDecimal(5000))))
    ));
}
