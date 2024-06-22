package middle.example.gpb.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequestV2(@NotNull Long userId, @NotBlank String userName) {}
