package middle.example.gpb.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AccountListResponse {

    private final UUID accountId;
    private String accountName;
    private BigDecimal amount;
}
