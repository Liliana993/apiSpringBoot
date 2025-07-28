package com.api.apiSpring.controller;

import com.api.apiSpring.domain.paciente.*;
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
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity registro(@RequestBody @Valid DatosRegistroPaciente datos, UriComponentsBuilder uriComponentsBuilder){
        var paciente = new Paciente(datos);
        repository.save(paciente);

        var uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetallePaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaPaciente>>  listarPaciente(@PageableDefault(size=10, sort={"nombre"}) Pageable pageable){
        var page = repository.findAllByActivoTrue(pageable).map(DatosListaPaciente::new);

        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizarPaciente(@RequestBody @Valid DatosActualizacionPaciente datos){
        var paciente = repository.getReferenceById(datos.id());
        paciente.actualizarInformacion(datos);

        return ResponseEntity.ok(new DatosDetallePaciente(paciente));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPaciente(@PathVariable Long id){
        var paciente = repository.getReferenceById(id); //obtenemos el paciente
        paciente.elimiarPaciente(); //obtenemos el metodo del paciente

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity obtenerUnPaciente(@PathVariable Long id){
        var paciente = repository.getReferenceById(id); //obtenemos el paciente

        return ResponseEntity.ok(new DatosDetallePaciente(paciente));
    }

}
