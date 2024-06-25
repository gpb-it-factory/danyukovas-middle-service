package middle.example.gpb.gateways;

import lombok.Getter;
import middle.example.gpb.models.AccountsListResponseV2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Component
public class BackendRepositoryMock {

    private final Map<Long, List<AccountsListResponseV2>> repository = new HashMap<>(Map.of(
            1L, new ArrayList<>(),
            2L, new ArrayList<>(),
            3L, new ArrayList<>(),
            4L, new ArrayList<>(),
            5L, new ArrayList<>(),
            66L, List.of(
                    new AccountsListResponseV2(UUID.randomUUID(), "test1", new BigDecimal("5000.00")),
                    new AccountsListResponseV2(UUID.randomUUID(), "test2", new BigDecimal("6000.00"))
            )
    ));
}
