package com.api.apiSpring.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ulrimoHorario);

    boolean existsByMedicoIdAndFechaAndMotivoCancelamientoIsNull(Long idMedico, LocalDateTime fecha);
}
