package com.example.demo.controller;

import com.example.demo.model.Eleitor;
import com.example.demo.service.EleitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eleitores")
public class EleitorController {

    @Autowired
    private EleitorService eleitorService;

    @GetMapping
    public List<Eleitor> getAllEleitores() {
        return eleitorService.getAllEleitores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eleitor> getEleitorById(@PathVariable Long id) {
        return eleitorService.getEleitorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Eleitor> createEleitor(@RequestParam String nome) {
        Eleitor eleitor = new Eleitor();
        eleitor.setNome(nome);
        eleitor.setVotou(false);
        Eleitor novoEleitor = eleitorService.createEleitor(eleitor);
        return ResponseEntity.ok(novoEleitor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Eleitor> updateEleitor(@PathVariable Long id, @RequestParam String nome) {
        return ResponseEntity.ok(eleitorService.updateEleitor(id, nome));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEleitor(@PathVariable Long id) {
        eleitorService.deleteEleitor(id);
        return ResponseEntity.noContent().build();
    }
}
