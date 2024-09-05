package com.example.demo.model;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean aberta;

    @ManyToOne
    private Cargo cargo;

    @ManyToMany
    @JoinTable(
        name = "sessao_candidato",
        joinColumns = @JoinColumn(name = "sessao_id"),
        inverseJoinColumns = @JoinColumn(name = "candidato_id")
    )
    private Set<Candidato> candidatos = new HashSet<>();

    @ElementCollection
    private Map<Long, Integer> votosPorCandidato = new HashMap<>(); 


    @ManyToMany
    @JoinTable(
        name = "sessao_eleitor",
        joinColumns = @JoinColumn(name = "sessao_id"),
        inverseJoinColumns = @JoinColumn(name = "eleitor_id")
    )
    private Set<Eleitor> eleitores = new HashSet<>();

    public Sessao(){
        this.aberta = true;
    }

    public Sessao(Cargo cargo) {
        this.aberta = true;
        this.cargo = cargo; 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Candidato> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(Set<Candidato> candidatos) {
        this.candidatos = candidatos;
    }

    public boolean isAberta() {
        return aberta;
    }

    public void setAberta(boolean aberta) {
        this.aberta = aberta;
    }

    public void addCandidato(Candidato candidato) {
        this.candidatos.add(candidato);
        votosPorCandidato.putIfAbsent(candidato.getId(), 0); 
    }


    public boolean votar(Eleitor eleitor, Candidato candidato) {
        if (!aberta) {
            throw new IllegalStateException("A sessão está fechada e não é possível votar.");
        }
        if (eleitores.contains(eleitor)) {
            throw new IllegalStateException("Este eleitor já votou.");
        }
        if (!candidatos.contains(candidato)) {
            throw new IllegalArgumentException("Candidato inválido.");
        }
        Long idCandidato = candidato.getId();

        votosPorCandidato.put(idCandidato, votosPorCandidato.get(idCandidato) + 1);
        eleitores.add(eleitor);
        return true;
    }

    public Map<Long, Integer> getVotosPorCandidato(){
        return votosPorCandidato;
    }

    public void fecharSessao() {
        if (eleitores.size() == 1) {
            votosPorCandidato.clear();
        }
        this.aberta = false;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Cargo getCargo() {
        return this.cargo;
    }
}
