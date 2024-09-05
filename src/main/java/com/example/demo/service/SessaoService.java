package com.example.demo.service;

import com.example.demo.model.Candidato;
import com.example.demo.model.Cargo;
import com.example.demo.model.Eleitor;
import com.example.demo.model.Sessao;
import com.example.demo.repository.SessaoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private CargoService cargoService; 

    public Sessao criarSessao(Long cargoId) {
        // if (sessaoRepository.existsByAbertaTrue()) {
        //     throw new IllegalStateException("Já existe uma sessão aberta. Feche-a antes de abrir uma nova.");
        // }

        Optional<Cargo> cargoSessao = cargoService.getCargoById(cargoId);
        if (cargoSessao.isPresent()){
            Sessao novaSessao = new Sessao(cargoSessao.get());
            novaSessao.setAberta(true);
            return sessaoRepository.save(novaSessao);
        }
        else {
            throw new IllegalArgumentException("Cargo não existe.");
        }
        
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

    public String obterVotos(Long sessaoId) {
        Sessao sessao = buscarSessaoPorId(sessaoId);

        if (sessao.isAberta()) {
            throw new IllegalArgumentException("Sessão ainda está aberta.");
        }

        return gerarRelatorio(sessao.getVotosPorCandidato(), sessao.getCargo().getNome());
    }

    public String gerarRelatorio(Map<Long, Integer> votosPorCandidato, String cargo) {
        StringBuilder relatorio = new StringBuilder();

        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        relatorio.append("Data relatório: ").append(agora.format(formatter)).append("\n");

        relatorio.append("Cargo: ").append(cargo).append("\n");

        relatorio.append("Candidatos      Votos\n");
        int totalVotos = 0;
        Optional<Map.Entry<Long, Integer>> vencedor = Optional.empty();
        String nomeVencedor = "Não há vencedor";

        for (Map.Entry<Long, Integer> entry : votosPorCandidato.entrySet()) {
            Long idCandidato = entry.getKey();
            Integer votos = entry.getValue();
            Optional<Candidato> candidato = candidatoService.getCandidatoById(idCandidato); 
            String nomeCandidato;
            if (candidato.isPresent()) {
                nomeCandidato = candidato.get().getNome();
            } else {
                throw new IllegalArgumentException("Candidato não encontrado");
            }

            relatorio.append(String.format("%-15s %d\n", nomeCandidato, votos));
            totalVotos += votos;

            if ((vencedor.isEmpty() || votos > vencedor.get().getValue()) && votos > 0) {
                vencedor = Optional.of(entry);
                nomeVencedor = nomeCandidato;
            }
        }

        relatorio.append("Total de votos ").append(totalVotos).append("\n");

       relatorio.append("Vencedor: ").append(nomeVencedor).append("\n");

        return relatorio.toString();
    }
    
}
