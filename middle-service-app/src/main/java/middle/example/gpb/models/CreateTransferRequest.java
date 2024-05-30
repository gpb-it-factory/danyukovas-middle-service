package middle.example.gpb.models;

import java.math.BigDecimal;

public record CreateTransferRequest(long fromId, long toId, BigDecimal amount) {}
