package com.api.apiSpring.controller;

import com.api.apiSpring.domain.medico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @Transactional //para agregar datos a nuestra base de datos
    @PostMapping //para registrar un nuevo medico
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroMedico datos, UriComponentsBuilder uriComponentsBuilder) {
        var medico = new Medico(datos);
        repository.save(medico);

        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaMedico>> listarMedico(@PageableDefault(size=10, sort={"nombre"}) Pageable pageable){
        var page = repository.findAllByActivoTrue(pageable).map(DatosListaMedico::new);

        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizacionMedico datos){
        var medico = repository.getReferenceById(datos.id());
        medico.actualizarInformacion(datos);

        return ResponseEntity.ok(new DatosDetalleMedico(medico));
    }
    
    @Transactional
    @DeleteMapping("/{id}")
    //ResponseEntity nos permite devolver un cierto código dependiendo de nuestra request. en este caso es 204
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        var medico = repository.getReferenceById(id); //obtenemos el medico
        medico.elimiarMedico(); //obtenemos el metodo del medico

        //Retorno
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    //ResponseEntity nos permite devolver un cierto código dependiendo de nuestra request. en este caso es 204
    public ResponseEntity detallarUnMedico(@PathVariable Long id){
        var medico = repository.getReferenceById(id); //obtenemos el medico

        //Retorno
        return ResponseEntity.ok(new DatosDetalleMedico(medico));
    }

}