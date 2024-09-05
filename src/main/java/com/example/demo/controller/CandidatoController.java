package com.example.demo.controller;

import com.example.demo.model.Candidato;
import com.example.demo.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @GetMapping
    public List<Candidato> getAllCandidatos() {
        return candidatoService.getAllCandidatos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidato> getCandidatoById(@PathVariable Long id) {
        return candidatoService.getCandidatoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Candidato> createCandidato(@RequestParam String nome) {
        Candidato candidato = new Candidato();
        candidato.setNome(nome);
        candidato.setParticipando(false);
        Candidato novoCandidato = candidatoService.createCandidato(candidato);
        return ResponseEntity.ok(novoCandidato);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Candidato> updateCandidato(@PathVariable Long id, @RequestParam String nome) {
        return ResponseEntity.ok(candidatoService.updateCandidato(id, nome));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidato(@PathVariable Long id) {
        candidatoService.deleteCandidato(id);
        return ResponseEntity.noContent().build();
    }
}
