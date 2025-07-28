package com.api.apiSpring.domain.consulta.validaciones;

import com.api.apiSpring.domain.ValidacionException;
import com.api.apiSpring.domain.consulta.ConsultaRepository;
import com.api.apiSpring.domain.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarMedicoConOtraConsultaEnElMismoHorario implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosReservaConsulta datos){
        var medicoTieneOtraConsultaEnElMismoHorario = repository.existsByMedicoIdAndFechaAndMotivoCancelamientoIsNull(datos.idMedico(), datos.fecha());
        if(medicoTieneOtraConsultaEnElMismoHorario){
            throw new ValidacionException("El médico ya tiene una consulta en esa fecha y horario.");
        }
    }
}
