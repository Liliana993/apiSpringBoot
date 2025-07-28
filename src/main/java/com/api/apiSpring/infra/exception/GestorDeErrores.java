package com.api.apiSpring.infra.exception;

import com.api.apiSpring.domain.ValidacionException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //anotacion para que spring lo reconozca
public class GestorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class) //clase a tratar el error 404 notFound
    public ResponseEntity gestiornarError404(){

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //clase a tratar el error 400 badRequest
    public ResponseEntity gestiornarError400(MethodArgumentNotValidException ex){
        var errores = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errores.stream().map(DatosErrorValidacion::new).toList());
    }

    @ExceptionHandler(ValidacionException.class) //clase a tratar el error 400 badRequest
    public ResponseEntity tratarErrorDeValidacion(ValidacionException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    //record para indicar donde fallo nuestro validacion de campos
    public record DatosErrorValidacion(
            String campo,
            String mensaje){

        public DatosErrorValidacion(FieldError error){
            this(
                    error.getField(),
                    error.getDefaultMessage());
        }
    }

    public ResponseEntity gestionarErrorBadCredentials(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Credenciales inválidas");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity gestionarErrorAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falla en la autenticación");
    }

    public ResponseEntity gestionarError500(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " +ex.getLocalizedMessage());
    }
}
