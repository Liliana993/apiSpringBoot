package com.api.apiSpring.domain.medico;

import com.api.apiSpring.domain.direccion.DatosDireccion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DatosRegistroMedico(
        @NotBlank String nombre, //que el nombre no venga vacio
        @NotBlank @Email String email, //validacion para email
        @NotBlank String telefono,
        @NotBlank @Pattern(regexp = "\\d{7,9}") String documento,//validacion para documento con expresion regular entre 7 y 9 numeros
        @NotNull Especialidad especialidad,
        @NotNull @Valid DatosDireccion direccion
) {
}
