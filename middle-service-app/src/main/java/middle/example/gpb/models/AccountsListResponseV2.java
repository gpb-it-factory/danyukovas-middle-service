package middle.example.gpb.models;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountsListResponseV2(UUID accountId, String accountName, BigDecimal amount) {}
