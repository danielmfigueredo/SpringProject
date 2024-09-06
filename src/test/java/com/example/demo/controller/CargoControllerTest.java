package com.example.demo.controller;

import com.example.demo.model.Cargo;
import com.example.demo.service.CargoService;
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

class CargoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CargoService cargoService;

    @InjectMocks
    private CargoController cargoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cargoController).build();
    }

    @Test
    void getAllCargos_ReturnsEmptyList() throws Exception {
        when(cargoService.getAllCargos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/cargos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(cargoService, times(1)).getAllCargos();
    }

    @Test
    void getCargoById_ReturnsCargo() throws Exception {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setNome("Prefeito");

        when(cargoService.getCargoById(1L)).thenReturn(Optional.of(cargo));

        mockMvc.perform(get("/api/cargos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Prefeito"));

        verify(cargoService, times(1)).getCargoById(1L);
    }
}