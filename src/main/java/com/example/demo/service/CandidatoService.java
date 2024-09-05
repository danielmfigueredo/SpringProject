package com.example.demo.service;

import com.example.demo.model.Candidato;
import com.example.demo.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    public List<Candidato> getAllCandidatos() {
        return candidatoRepository.findAll();
    }

    public Optional<Candidato> getCandidatoById(Long id) {
        return candidatoRepository.findById(id);
    }

    public Candidato createCandidato(Candidato candidato) {
        return candidatoRepository.save(candidato);
    }

    public Candidato updateCandidato(Long id, Candidato candidatoDetails) {
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidato not found with id " + id));

        candidato.setNome(candidatoDetails.getNome());
        
        return candidatoRepository.save(candidato);
    }

    public void deleteCandidato(Long id) {
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidato not found with id " + id));
        candidatoRepository.delete(candidato);
    }
}
