package com.api.apiSpring.domain.consulta.validaciones;

import com.api.apiSpring.domain.ValidacionException;
import com.api.apiSpring.domain.consulta.ConsultaRepository;
import com.api.apiSpring.domain.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPacienteOtraConsultaMismoDia implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosReservaConsulta datos){
        var primerHorario = datos.fecha().withHour(7);
        var ulrimoHorario = datos.fecha().withHour(18);
        var pacienteTieneOtraConsultaEnElDia = repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primerHorario, ulrimoHorario);
        if(pacienteTieneOtraConsultaEnElDia){
            throw new ValidacionException("Paciente ya tiene reservada consulta para ese día.");
        }

    }
}
