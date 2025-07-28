package com.api.apiSpring.controller;

import com.api.apiSpring.domain.consulta.DatosCancelamentoConsulta;
import com.api.apiSpring.domain.consulta.DatosDetailsConsulta;
import com.api.apiSpring.domain.consulta.DatosReservaConsulta;
import com.api.apiSpring.domain.consulta.ReservaDeConsultas;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ReservaDeConsultas reserva;

    @PostMapping
    @Transactional
    public ResponseEntity reservar(@RequestBody @Valid DatosReservaConsulta datos){

        var detalleConsulta = reserva.reservar(datos);

        return ResponseEntity.ok(detalleConsulta);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DatosCancelamentoConsulta datos){
        reserva.cancelar(datos);
        return ResponseEntity.noContent().build();
    }
}
