package com.api.apiSpring.domain.consulta.validaciones;

import com.api.apiSpring.domain.ValidacionException;
import com.api.apiSpring.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacionFueraDelHorarioConsulta implements ValidadorDeConsultas {


    public void validar(DatosReservaConsulta datos){
        var fechaConsulta = datos.fecha();
        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioApertura = fechaConsulta.getHour() < 7;
        var horarioPostCierre = fechaConsulta.getHour() > 18;

        if(domingo || horarioApertura || horarioPostCierre){
            throw new ValidacionException("Horario/día fuera del horario o día permitido.");
        }
    }
}
