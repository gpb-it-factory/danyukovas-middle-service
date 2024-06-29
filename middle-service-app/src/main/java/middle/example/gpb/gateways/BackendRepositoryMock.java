package middle.example.gpb.gateways;

import lombok.Getter;
import middle.example.gpb.models.AccountsListResponseV2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Component
public class BackendRepositoryMock {

    private final Map<Long, Optional<AccountsListResponseV2>> repository = new ConcurrentHashMap<>(Map.of(
            1L, Optional.empty(),
            2L, Optional.empty(),
            3L, Optional.empty(),
            4L, Optional.empty(),
            5L, Optional.empty()
    ));
}
