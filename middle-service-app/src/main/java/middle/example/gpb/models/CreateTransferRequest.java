package middle.example.gpb.models;

import java.math.BigDecimal;

public record CreateTransferRequest(String from, String to, BigDecimal amount) {}
