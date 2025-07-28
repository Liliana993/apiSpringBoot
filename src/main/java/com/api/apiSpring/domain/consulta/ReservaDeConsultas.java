package com.api.apiSpring.domain.consulta;

import com.api.apiSpring.domain.ValidacionException;
import com.api.apiSpring.domain.consulta.validaciones.ValidadorCancelamientoDeConsulta;
import com.api.apiSpring.domain.consulta.validaciones.ValidadorDeConsultas;
import com.api.apiSpring.domain.medico.Medico;
import com.api.apiSpring.domain.medico.MedicoRepository;
import com.api.apiSpring.domain.paciente.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired //patrón strategy
    private List<ValidadorDeConsultas> validaciones;

    @Autowired
    private List<ValidadorCancelamientoDeConsulta> validadorCancelamiento;

    public DatosDetailsConsulta reservar(DatosReservaConsulta datos){

        if(!pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidacionException("No existe un paciente con el Id informado");

        }

        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionException("No existe un médico con el Id informado");

        }

        //Implementando Validaciones
        //Utilizando un forEach podemos utilizar todas las validaciones de nuestra regla de negocios
        validaciones.forEach(v -> v.validar(datos));


        //obtener los medicos y pacientes de la base de datos
        var medico = elegirMedico(datos);
        if(medico == null){
            throw new ValidacionException("No existe un médico disponible en ese horario");
        }

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var consulta = new Consulta(null, medico, paciente, datos.fecha(), null);

        consultaRepository.save(consulta);

        return new DatosDetailsConsulta(consulta);
    }


    private Medico elegirMedico(DatosReservaConsulta datos) {
        if(datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad() == null){
            throw new ValidacionException("Es necesario elegir una especialidad, cuando no se elije un médico.");
        }
        return medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(datos.especialidad(), datos.fecha());
    }

    public void cancelar(@Valid DatosCancelamentoConsulta datos) {
        if(!consultaRepository.existsById(datos.idConsulta())){
            throw new ValidacionException("Id de la consulta no encontrado.");
        }

        validadorCancelamiento.forEach(v -> v.validar(datos));
        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
}
