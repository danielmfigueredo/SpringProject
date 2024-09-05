package com.example.demo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EleitorTest {

    @Test
    public void testEleitorCreation() {
        Eleitor eleitor = new Eleitor();
        assertNotNull(eleitor, "Eleitor não deve ser NULL");
    }

    @Test
    public void testSetAndGetId() {
        Eleitor eleitor = new Eleitor();
        Long expectedId = 1L;
        eleitor.setId(expectedId);
        assertEquals(expectedId, eleitor.getId(), "Checagem ID");
    }

    @Test
    public void testSetAndGetNome() {
        Eleitor eleitor = new Eleitor();
        String expectedNome = "Eleitor Teste";
        eleitor.setNome(expectedNome);
        assertEquals(expectedNome, eleitor.getNome(), "Chegagem Nome");
    }

    @Test
    public void testSetNomeWithNull() {
        Eleitor eleitor = new Eleitor();
        eleitor.setNome(null);
        assertNull(eleitor.getNome(), "Testa nome NULL");
    }
}