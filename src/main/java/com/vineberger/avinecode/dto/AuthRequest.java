package com.vineberger.avinecode.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class AuthRequest {

    @NotNull
    @Email
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 5)
    private String password;
}
