package com.api.apiSpring.domain.consulta.validaciones;

import com.api.apiSpring.domain.ValidacionException;
import com.api.apiSpring.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacionConsultaAnticipacion implements ValidadorDeConsultas {

    public void validar(DatosReservaConsulta datos){
        var fechaConsulta = datos.fecha();
        var ahora = LocalDateTime.now();
        var diferenciaEnMinutos = Duration.between(ahora, fechaConsulta).toMinutes();

        if(diferenciaEnMinutos < 30){
            throw new ValidacionException("Horario seleccionado es menor a 30 minutos de anticipación");
        }
    }
}
