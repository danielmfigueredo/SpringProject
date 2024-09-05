package com.example.demo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CargoTest {

    @Test
    public void testCargoCreation() {
        Cargo cargo = new Cargo();
        assertNotNull(cargo, "Cargo não deve ser NULL");
    }

    @Test
    public void testSetAndGetId() {
        Cargo cargo = new Cargo();
        Long expectedId = 1L;
        cargo.setId(expectedId);
        assertEquals(expectedId, cargo.getId(), "Checagem ID");
    }

    @Test
    public void testSetAndGetNome() {
        Cargo cargo = new Cargo();
        String expectedNome = "Cargo Teste";
        cargo.setNome(expectedNome);
        assertEquals(expectedNome, cargo.getNome(), "Chegagem Nome");
    }

    @Test
    public void testSetNomeWithNull() {
        Cargo cargo = new Cargo();
        cargo.setNome(null);
        assertNull(cargo.getNome(), "Testa nome NULL");
    }
}