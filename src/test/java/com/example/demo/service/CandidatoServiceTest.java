package com.example.demo.service;

import com.example.demo.model.Candidato;
import com.example.demo.repository.CandidatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CandidatoServiceTest {

    @Mock
    private CandidatoRepository candidatoRepository;

    @InjectMocks
    private CandidatoService candidatoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCandidato() {
        Candidato candidato = new Candidato();
        candidato.setNome("João");

        when(candidatoRepository.save(any(Candidato.class))).thenReturn(candidato);

        Candidato novoCandidato = candidatoService.createCandidato(candidato);

        assertNotNull(novoCandidato);
        assertEquals("João", novoCandidato.getNome());
        verify(candidatoRepository, times(1)).save(candidato);
    }

    @Test
    void testGetCandidatoById() {
        Candidato candidato = new Candidato();
        candidato.setId(1L);
        candidato.setNome("Maria");

        when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));

        Optional<Candidato> foundCandidato = candidatoService.getCandidatoById(1L);

        assertTrue(foundCandidato.isPresent());
        assertEquals("Maria", foundCandidato.get().getNome());
        verify(candidatoRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateCandidato() {
        Candidato candidato = new Candidato();
        candidato.setId(1L);
        candidato.setNome("Ana");

        when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));
        when(candidatoRepository.save(any(Candidato.class))).thenReturn(candidato);

        Candidato updatedCandidato = candidatoService.updateCandidato(1L, "Ana Clara");

        assertNotNull(updatedCandidato);
        assertEquals("Ana Clara", updatedCandidato.getNome());
        verify(candidatoRepository, times(1)).findById(1L);
        verify(candidatoRepository, times(1)).save(candidato);
    }

    @Test
    void testDeleteCandidato() {
        Candidato candidato = new Candidato();
        candidato.setId(1L);
        candidato.setParticipando(false);

        when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));

        candidatoService.deleteCandidato(1L);

        verify(candidatoRepository, times(1)).findById(1L);
        verify(candidatoRepository, times(1)).delete(candidato);
    }

    @Test
    void testDeleteCandidatoComParticipacao() {
        Candidato candidato = new Candidato();
        candidato.setId(1L);
        candidato.setParticipando(true);  

        when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            candidatoService.deleteCandidato(1L);
        });

        assertEquals("Candidato já votado, não pode ser deletado", exception.getMessage());
        verify(candidatoRepository, times(1)).findById(1L);
        verify(candidatoRepository, times(0)).delete(candidato); 
    }

    @Test
    void testCandidatoNotFoundForDelete() {
        when(candidatoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            candidatoService.deleteCandidato(1L);
        });

        assertEquals("Candidato not found with id 1", exception.getMessage());
        verify(candidatoRepository, times(1)).findById(1L);
        verify(candidatoRepository, times(0)).delete(any(Candidato.class)); 
    }
}
