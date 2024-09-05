package com.example.demo.service;

import com.example.demo.model.Candidato;
import com.example.demo.model.Eleitor;
import com.example.demo.model.Sessao;
import com.example.demo.repository.SessaoRepository;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private CandidatoService candidatoService; 

    @Autowired
    private EleitorService eleitorService; 

    // public Sessao criarSessao() {
    //     Sessao sessao = new Sessao();
    //     return sessaoRepository.save(sessao);
    // }

    public Sessao criarSessao() {
        // if (sessaoRepository.existsByAbertaTrue()) {
        //     throw new IllegalStateException("Já existe uma sessão aberta. Feche-a antes de abrir uma nova.");
        // }

        Sessao novaSessao = new Sessao();
        novaSessao.setAberta(true);
        return sessaoRepository.save(novaSessao);
    }

    public Sessao adicionarCandidato(Long sessaoId, Long candidatoId) {
        Sessao sessao = buscarSessaoPorId(sessaoId);
        
        Optional<Candidato> candidatoOptional = candidatoService.getCandidatoById(candidatoId);
        if (candidatoOptional.isEmpty()) {
            throw new IllegalArgumentException("Candidato não encontrado.");
        }
        
        Candidato candidato = candidatoOptional.get();
        sessao.addCandidato(candidato);
        return sessaoRepository.save(sessao);
    }
    // public void votar(Long sessaoId, Long idEleitor, Long idCandidato) {
    //     Sessao sessao = buscarSessaoPorId(sessaoId);
    //     sessao.votar(idEleitor, idCandidato);
    //     sessaoRepository.save(sessao);
    // }

    public void votar(Long sessaoId, Long eleitorId, Long candidatoId) {
        Sessao sessao = buscarSessaoPorId(sessaoId);

        Optional<Eleitor> eleitorOptional = eleitorService.getEleitorById(eleitorId);
        if (eleitorOptional.isEmpty()) {
            throw new IllegalArgumentException("Eleitor não encontrado.");
        }

        Optional<Candidato> candidatoOptional = candidatoService.getCandidatoById(candidatoId);
        if (candidatoOptional.isEmpty()) {
            throw new IllegalArgumentException("Candidato não encontrado.");
        }

        Eleitor eleitor = eleitorOptional.get();
        Candidato candidato = candidatoOptional.get();
        
        sessao.votar(eleitor, candidato);
        sessaoRepository.save(sessao);
    }

    public void fecharSessao(Long sessaoId) {
        Sessao sessao = buscarSessaoPorId(sessaoId);
        sessao.fecharSessao();
        sessaoRepository.save(sessao);
    }

    private Sessao buscarSessaoPorId(Long sessaoId) {
        Optional<Sessao> sessaoOptional = sessaoRepository.findById(sessaoId);
        if (sessaoOptional.isEmpty()) {
            throw new IllegalArgumentException("Sessão não encontrada.");
        }
        return sessaoOptional.get();
    }

    public Map<Long, Integer> obterVotos(Long sessaoId) {
        Sessao sessao = buscarSessaoPorId(sessaoId);

        if (sessao.isAberta()) {
            throw new IllegalArgumentException("Sessão ainda está aberta.");
        }

        return sessao.getVotosPorCandidato();
    }

    
}
