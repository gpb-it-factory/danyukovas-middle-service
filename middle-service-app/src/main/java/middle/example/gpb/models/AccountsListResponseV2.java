package middle.example.gpb.models;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountsListResponseV2(UUID accountId, String accountName, BigDecimal amount) {

    @Override
    public String toString() {
        return "Название аккаунта: " + accountName + "\n" +
                "Сумма счета: " + amount;
    }
}
