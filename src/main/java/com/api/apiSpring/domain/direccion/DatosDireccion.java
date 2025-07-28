package com.api.apiSpring.domain.direccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosDireccion(
        @NotBlank String calle,
        String numero,
        String complemento,
        @NotBlank String barrio,
        @NotBlank String ciudad,
        @NotBlank @Pattern(regexp = "\\d{4,8}") String codigo,
        @NotBlank String provincia
) {
}
