package com.api.apiSpring.domain.medico;

import com.api.apiSpring.domain.consulta.Consulta;
import com.api.apiSpring.domain.direccion.DatosDireccion;
import com.api.apiSpring.domain.paciente.DatosRegistroPaciente;
import com.api.apiSpring.domain.paciente.Paciente;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //capa de persistencia
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EntityManager en;

    @Test
    @DisplayName("Deberia devolver null cuando el medico buscado existe pero no esta disponible en esa fecha.")
    void elegirMedicoAleatorioDisponibleEscenario1() {
        //given a arrange
        var lunesSiguienteALas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
        var medico = registrarMedico("Medico1", "medico@gmail.com", "23456789", Especialidad.Cardiologia);
        var paciente = registrarPaciente("Juan", "juncho@gmail.com", "34521678");
        registrarConsulta(medico, paciente, lunesSiguienteALas10);

        //when o act
        var medicoLibre  = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.Cardiologia, lunesSiguienteALas10);
        //then o assert
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Deberia devolver un medico cuando el medico buscado esta disponible en esa fecha.")
    @WithMockUser
    void elegirMedicoAleatorioDisponibleEscenario2() {
        //given a arrange
        var lunesSiguienteALas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var medico = registrarMedico("Medico1", "medico@gmail.com", "23456789", Especialidad.Cardiologia);
        //when o act
        var medicoLibre  = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.Cardiologia, lunesSiguienteALas10);

        //then o assert
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha){
        en.persist(new Consulta(null, medico, paciente, fecha, null));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad){
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        en.persist(medico);
        return  medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento){
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        en.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad){
        return new DatosRegistroMedico(
                nombre,
                email,
                "098765321",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento){
        return new DatosRegistroPaciente(
                nombre,
                email,
                documento,
                "123456789",
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion(){
        return new DatosDireccion(
                "calle x",
                "234",
                "edificio",
                "Centro",
                "Posadas",
                "3335",
                "Misiones"
        );
    }

}