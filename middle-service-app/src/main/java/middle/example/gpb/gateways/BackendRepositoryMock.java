package middle.example.gpb.gateways;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import middle.example.gpb.models.AccountsListResponseV2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
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
                    new AccountsListResponseV2(UUID.fromString("a46e9ea0-917a-4126-9676-8053b8536241"), "test", new BigDecimal(5000)))),
            7L, new ArrayList<>(List.of(
            new AccountsListResponseV2(UUID.fromString("a46e9ea0-917a-4126-9676-8053b8536242"), "test2", new BigDecimal(1000))))
    ));

    private final Map<Long, String> users = new ConcurrentHashMap<>(Map.of(
            1L, "test1",
            2L, "test2",
            3L, "test3",
            4L, "test4",
            5L, "test5",
            6L, "test6",
            7L, "test7"
    ));

    public void updateAccountList(Long id, AccountsListResponseV2 newAccount) {
        log.debug("Обновление счета аккаунта пользователя {}.", id);
        List<AccountsListResponseV2> accountList = repository.get(id);
            accountList.set(0, newAccount);
    }

    public Optional<Long> findAccByName(String name) {
        log.debug("Поиск аккаунта пользователя с username'ом {}, которому переводят деньги.", name);
        return users.entrySet().stream()
                .filter(v -> v.getValue().contains(name))
                .map(Map.Entry::getKey)
                .findFirst();
    }
}
