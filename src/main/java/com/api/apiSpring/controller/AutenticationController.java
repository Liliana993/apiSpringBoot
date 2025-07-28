package com.api.apiSpring.controller;

import com.api.apiSpring.domain.usuario.DatosAutentication;
import com.api.apiSpring.domain.usuario.Usuario;
import com.api.apiSpring.infra.security.DatosTokenJWT;
import com.api.apiSpring.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticationController {

    @Autowired //inyecciónde dependencia
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity iniciarSession(@RequestBody @Valid DatosAutentication datos){

        //Generamos eltoken con los datos de login
        var autenticationToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.contrasena());
        var autentication = manager.authenticate(autenticationToken);

        var tokenJWT = tokenService.generarToken((Usuario) autentication.getPrincipal());

        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }

}
