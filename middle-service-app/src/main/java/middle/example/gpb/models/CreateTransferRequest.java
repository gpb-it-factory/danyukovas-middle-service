package middle.example.gpb.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateTransferRequest(@NotBlank String from, @NotBlank String to, @DecimalMin(value = "0.0", inclusive = false) BigDecimal amount) {}
