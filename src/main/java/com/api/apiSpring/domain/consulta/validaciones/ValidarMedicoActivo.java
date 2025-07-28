package com.api.apiSpring.domain.consulta.validaciones;

import com.api.apiSpring.domain.ValidacionException;
import com.api.apiSpring.domain.consulta.DatosReservaConsulta;
import com.api.apiSpring.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarMedicoActivo implements ValidadorDeConsultas {

    @Autowired
    private MedicoRepository repository;

    public void validar(DatosReservaConsulta datos){

        if(datos.idMedico() == null){
            return;
        }
        var medicoIsActivo = repository.findActivoById(datos.idMedico());
        if(!medicoIsActivo){
            throw new ValidacionException("No puede reservar consulta con médico excluido.");
        }
    }
}
