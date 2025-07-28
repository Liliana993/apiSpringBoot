package com.api.apiSpring.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DatosCancelamentoConsulta(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCanclamiento motivo
) {
}
