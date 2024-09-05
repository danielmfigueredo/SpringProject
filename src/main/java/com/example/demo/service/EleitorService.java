package com.example.demo.service;

import com.example.demo.model.Eleitor;
import com.example.demo.repository.EleitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EleitorService {

    @Autowired
    private EleitorRepository eleitorRepository;

    public List<Eleitor> getAllEleitores() {
        return eleitorRepository.findAll();
    }

    public Optional<Eleitor> getEleitorById(Long id) {
        return eleitorRepository.findById(id);
    }

    public Eleitor createEleitor(Eleitor eleitor) {
        return eleitorRepository.save(eleitor);
    }

    public Eleitor updateEleitor(Long id, String nome) {
        Eleitor eleitor = eleitorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Eleitor not found with id " + id));

        eleitor.setNome(nome);
        
        return eleitorRepository.save(eleitor);
    }

    public void deleteEleitor(Long id) {
        Eleitor eleitor = eleitorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Eleitor not found with id " + id));

        if (!eleitor.isVotou()) {
            eleitorRepository.delete(eleitor);
        }

        else{
            throw new IllegalArgumentException("Eleitor já votou, não pode ser deletado");
        }
    }
}
