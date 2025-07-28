package com.api.apiSpring.controller;

import com.api.apiSpring.domain.consulta.DatosDetailsConsulta;
import com.api.apiSpring.domain.consulta.DatosReservaConsulta;
import com.api.apiSpring.domain.consulta.ReservaDeConsultas;
import com.api.apiSpring.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosReservaConsulta> datosReservaConsultaJson;

    @Autowired
    private JacksonTester<DatosDetailsConsulta> datosDetailsConsultaJson;

    @MockBean
    private ReservaDeConsultas reservaDeConsultas;


    @Test
    @DisplayName("Deberian devolver http 400 cuando la request no contenga datos")
    @WithMockUser
    void reservar_escenario1() throws Exception {
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deberian devolver http 200 cuando la request reciba un json válido.")
    @WithMockUser
    void reservar_escenario2() throws Exception {
        var fecha = LocalDateTime.now().plusHours(1);
        var especialidad = Especialidad.Cardiologia;

        var datosDetalle = new DatosDetailsConsulta(null, 2l, 5l, fecha);
        when(reservaDeConsultas.reservar(any())).thenReturn(datosDetalle);

        var response = mvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosReservaConsultaJson.
                                write(new DatosReservaConsulta(2l,2l, fecha, especialidad)
                                ).getJson()
                        ))
                .andReturn().getResponse();

        var jsonEsperado = datosDetailsConsultaJson.write(datosDetalle).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}