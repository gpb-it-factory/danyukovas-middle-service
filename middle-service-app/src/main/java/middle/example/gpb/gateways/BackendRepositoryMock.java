package middle.example.gpb.gateways;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class BackendRepositoryMock {

    private final Map<Long, Map<String, BigDecimal>> repository = new HashMap<>(Map.of(
            1L, new HashMap<>(),
            2L, new HashMap<>(),
            3L, new HashMap<>(),
            4L, new HashMap<>(),
            5L, new HashMap<>()
    ));
}
