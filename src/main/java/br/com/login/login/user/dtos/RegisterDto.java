package br.com.login.login.user.dtos;

import br.com.login.login.user.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotNull String login, @NotNull String password, @NotNull UserRole role) {
}
