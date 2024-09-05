package com.example.demo.controller;

import com.example.demo.model.Sessao;
import com.example.demo.service.SessaoService;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    // @PostMapping
    // public Sessao criarSessao() {
    //     return sessaoService.criarSessao();
    // }

    @PostMapping("/abrir")
    public ResponseEntity<Sessao> criarSessao() {
        try {
            Sessao novaSessao = sessaoService.criarSessao();
            return ResponseEntity.ok(novaSessao);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null); 
        }
    }

    @PostMapping("/{sessaoId}/candidatos/{candidatoId}")
    public Sessao adicionarCandidato(@PathVariable Long sessaoId, @PathVariable Long candidatoId) {
        return sessaoService.adicionarCandidato(sessaoId, candidatoId);
    }

    @PostMapping("/{sessaoId}/votar")
    public void votar(@PathVariable Long sessaoId, @RequestParam Long idEleitor, @RequestParam Long idCandidato) {
        sessaoService.votar(sessaoId, idEleitor, idCandidato);
    }

    @PostMapping("/{sessaoId}/fechar")
    public void fecharSessao(@PathVariable Long sessaoId) {
        sessaoService.fecharSessao(sessaoId);
    }

    @GetMapping("/{sessaoId}/votos")
    public ResponseEntity<Map<Long, Integer>> obterVotos(@PathVariable Long sessaoId) {
        try {
            Map<Long, Integer> votos = sessaoService.obterVotos(sessaoId);
            return ResponseEntity.ok(votos);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
