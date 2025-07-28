package com.api.apiSpring.domain.direccion;

import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Direccion {
    private String calle;
    private String numero;
    private String complemento;
    private String barrio;
    private String ciudad;
    private String codigo;
    private String provincia;

    public Direccion(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.complemento = direccion.complemento();
        this.barrio = direccion.barrio();
        this.ciudad = direccion.ciudad();
        this.codigo = direccion.codigo();
        this.provincia = direccion.provincia();
    }

    public void actualizarDireccion(@Valid DatosDireccion datos) {
        if(datos.calle() != null){
            this.calle = datos.calle();
        }
        if(datos.numero() != null){
            this.numero = datos.numero();
        }
        if(datos.complemento() != null){
            this.complemento = datos.complemento();
        }
        if(datos.barrio() != null){
            this.barrio = datos.barrio();
        }
        if(datos.ciudad() != null){
            this.ciudad = datos.ciudad();
        }
        if(datos.codigo() != null){
            this.codigo = datos.codigo();
        }
        if(datos.provincia() != null){
            this.provincia = datos.provincia();
        }
    }
}
