package com.api.apiSpring.domain.consulta.validaciones;

import com.api.apiSpring.domain.ValidacionException;
import com.api.apiSpring.domain.consulta.ConsultaRepository;
import com.api.apiSpring.domain.consulta.DatosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidarConsultaConAnticipacionCancelamiento")
public class ValidarHorarioConAnticipacion implements ValidadorCancelamientoDeConsulta{

    @Autowired
    private ConsultaRepository repository;


    @Override
    public void validar(DatosCancelamentoConsulta datos){
        var consulta = repository.getReferenceById(datos.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if(diferenciaEnHoras < 24){
            throw new ValidacionException("La consulta solo puede ser cancelada con 24hs de anticipación!");
        }
    }
}
