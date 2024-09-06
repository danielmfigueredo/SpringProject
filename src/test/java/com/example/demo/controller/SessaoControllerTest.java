package com.example.demo.controller;

import com.example.demo.model.Sessao;
import com.example.demo.service.SessaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SessaoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SessaoService sessaoService;

    @InjectMocks
    private SessaoController sessaoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sessaoController).build();
    }

    @Test
    void criarSessao_ReturnsNovaSessao() throws Exception {
        Sessao sessao = new Sessao();
        sessao.setId(1L);

        when(sessaoService.criarSessao(1L)).thenReturn(sessao);

        mockMvc.perform(post("/sessoes/abrir/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(sessaoService, times(1)).criarSessao(1L);
    }

    @Test
    void criarSessao_ReturnsBadRequestWhenException() throws Exception {
        when(sessaoService.criarSessao(1L)).thenThrow(new IllegalStateException());

        mockMvc.perform(post("/sessoes/abrir/1"))
                .andExpect(status().isBadRequest());

        verify(sessaoService, times(1)).criarSessao(1L);
    }

    @Test
    void adicionarCandidato_ReturnsSessao() throws Exception {
        Sessao sessao = new Sessao();
        sessao.setId(1L);

        when(sessaoService.adicionarCandidato(1L, 1L)).thenReturn(sessao);

        mockMvc.perform(post("/sessoes/1/candidatos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(sessaoService, times(1)).adicionarCandidato(1L, 1L);
    }

    @Test
    void votar_ReturnsOk() throws Exception {
        doNothing().when(sessaoService).votar(1L, 1L, 1L);

        mockMvc.perform(post("/sessoes/1/votar")
                .param("idEleitor", "1")
                .param("idCandidato", "1"))
                .andExpect(status().isOk());

        verify(sessaoService, times(1)).votar(1L, 1L, 1L);
    }

    @Test
    void fecharSessao_ReturnsOk() throws Exception {
        doNothing().when(sessaoService).fecharSessao(1L);

        mockMvc.perform(patch("/sessoes/1/fechar"))
                .andExpect(status().isOk());

        verify(sessaoService, times(1)).fecharSessao(1L);
    }
}