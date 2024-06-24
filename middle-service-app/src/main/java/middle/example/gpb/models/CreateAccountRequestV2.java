package middle.example.gpb.models;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountRequestV2(@NotBlank String accountName) {}
