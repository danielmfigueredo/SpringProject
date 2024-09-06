package com.example.demo.controller;

import com.example.demo.model.Candidato;
import com.example.demo.service.CandidatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CandidatoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CandidatoService candidatoService;

    @InjectMocks
    private CandidatoController candidatoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(candidatoController).build();
    }

    @Test
    void getAllCandidatos_ReturnsEmptyList() throws Exception {
        when(candidatoService.getAllCandidatos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/candidatos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(candidatoService, times(1)).getAllCandidatos();
    }

    @Test
    void getCandidatoById_ReturnsCandidato() throws Exception {
        Candidato candidato = new Candidato();
        candidato.setId(1L);
        candidato.setNome("João");

        when(candidatoService.getCandidatoById(1L)).thenReturn(Optional.of(candidato));

        mockMvc.perform(get("/api/candidatos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"));

        verify(candidatoService, times(1)).getCandidatoById(1L);
    }
}