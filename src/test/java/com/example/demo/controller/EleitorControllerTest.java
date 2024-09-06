package com.example.demo.controller;

import com.example.demo.model.Eleitor;
import com.example.demo.service.EleitorService;
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

class EleitorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EleitorService eleitorService;

    @InjectMocks
    private EleitorController eleitorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eleitorController).build();
    }

    @Test
    void getAllEleitors_ReturnsEmptyList() throws Exception {
        when(eleitorService.getAllEleitores()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/eleitores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(eleitorService, times(1)).getAllEleitores();
    }

    @Test
    void getEleitorById_ReturnsEleitor() throws Exception {
        Eleitor eleitor = new Eleitor();
        eleitor.setId(1L);
        eleitor.setNome("João");

        when(eleitorService.getEleitorById(1L)).thenReturn(Optional.of(eleitor));

        mockMvc.perform(get("/api/eleitores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"));

        verify(eleitorService, times(1)).getEleitorById(1L);
    }
}