package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SessaoTest {

    private Sessao sessao;
    private Candidato candidato;
    private Eleitor eleitor;
    private Cargo cargo;

    @BeforeEach
    public void setUp() {
        cargo = new Cargo();
        cargo.setId(1L);
        cargo.setNome("PRESIDENTE");

        sessao = new Sessao(cargo);
        candidato = new Candidato();
        candidato.setId(1L);
        candidato.setNome("Candidato Teste");

        eleitor = new Eleitor();
        eleitor.setId(1L);
        eleitor.setNome("Eleitor Teste");

        sessao.addCandidato(candidato);
    }

    @Test
    public void testCriarSessao() {
        assertNotNull(sessao, "Sessão não deveria ser nula após a criação");
        assertTrue(sessao.isAberta(), "A sessão deveria estar aberta após a criação");
    }

    @Test
    public void testAdicionarCandidato() {
        Candidato novoCandidato = new Candidato();
        novoCandidato.setId(2L);
        novoCandidato.setNome("Novo Candidato");

        sessao.addCandidato(novoCandidato);

        assertTrue(sessao.getCandidatos().contains(novoCandidato), "O candidato deveria estar na lista de candidatos");
        assertTrue(sessao.getVotosPorCandidato().containsKey(novoCandidato.getId()), "O mapa de votos deveria conter o novo candidato");
    }

    @Test
    public void testVotar() {
        boolean resultado = sessao.votar(eleitor, candidato);

        assertTrue(resultado, "A votação deveria ser bem-sucedida");
        assertTrue(sessao.getVotosPorCandidato().get(candidato.getId()) == 1, "O número de votos para o candidato deveria ser 1");
    }

    @Test
    public void testVotarSessaoFechada() {
        sessao.fecharSessao();

        assertThrows(IllegalStateException.class, () -> sessao.votar(eleitor, candidato), "Deveria lançar uma exceção ao tentar votar em uma sessão fechada");
    }

    @Test
    public void testVotarCandidatoInvalido() {
        Candidato candidatoInvalido = new Candidato();
        candidatoInvalido.setId(999L);

        assertThrows(IllegalArgumentException.class, () -> sessao.votar(eleitor, candidatoInvalido), "Deveria lançar uma exceção ao tentar votar em um candidato inválido");
    }

    @Test
    public void testFecharSessao() {
        sessao.fecharSessao();

        assertFalse(sessao.isAberta(), "A sessão deve estar fechada");
    }


    @Test
    public void testSetCargo() {
        Cargo novoCargo = new Cargo();
        novoCargo.setId(2L);
        novoCargo.setNome("VICE-PRESIDENTE");

        sessao.setCargo(novoCargo);

        assertEquals(novoCargo, sessao.getCargo(), "O cargo da sessão deve ser atualizado");
    }
}
