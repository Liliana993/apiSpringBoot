package com.api.apiSpring.domain.consulta.validaciones;

import com.api.apiSpring.domain.ValidacionException;
import com.api.apiSpring.domain.consulta.DatosReservaConsulta;
import com.api.apiSpring.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPacienteActivo implements ValidadorDeConsultas{

    @Autowired
    private PacienteRepository repository;

    public void validar(DatosReservaConsulta datos){
        var pacienteIsActivo = repository.findActivoById(datos.idPaciente());
        if(!pacienteIsActivo){
            throw new ValidacionException("Consulta no puede ser reservada con paciente excluido.");
        }
        }
    }