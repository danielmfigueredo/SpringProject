package com.example.demo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CandidatoTest {

    @Test
    public void testCandidatoCreation() {
        Candidato candidato = new Candidato();
        assertNotNull(candidato, "Candidato não deve ser NULL");
    }

    @Test
    public void testSetAndGetId() {
        Candidato candidato = new Candidato();
        Long expectedId = 1L;
        candidato.setId(expectedId);
        assertEquals(expectedId, candidato.getId(), "Checagem ID");
    }

    @Test
    public void testSetAndGetNome() {
        Candidato candidato = new Candidato();
        String expectedNome = "Candidato Teste";
        candidato.setNome(expectedNome);
        assertEquals(expectedNome, candidato.getNome(), "Chegagem Nome");
    }

    @Test
    public void testSetNomeWithNull() {
        Candidato candidato = new Candidato();
        candidato.setNome(null);
        assertNull(candidato.getNome(), "Testa nome NULL");
    }
}