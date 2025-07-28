package com.api.apiSpring.domain.consulta.cancelamiento;

import com.api.apiSpring.domain.consulta.DatosCancelamentoConsulta;
import com.api.apiSpring.domain.consulta.DatosReservaConsulta;
import com.api.apiSpring.domain.consulta.validaciones.ValidadorDeConsultas;
import org.springframework.stereotype.Component;

@Component("ValidarConsultaConAnticipacionReserva")
public class ValidarConsultaConAnticipacion implements ValidadorDeConsultas {

    public void validar(DatosReservaConsulta datos) {

    }
}
