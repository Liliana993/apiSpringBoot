package com.api.apiSpring.domain.consulta;

import java.time.LocalDateTime;

public record DatosDetailsConsulta(
        Long id,
        Long idMedico,
        Long idPaciente,
        LocalDateTime fecha
) {
    public DatosDetailsConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getFecha());
    }
}
