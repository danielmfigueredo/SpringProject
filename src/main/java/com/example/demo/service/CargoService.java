package com.example.demo.service;

import com.example.demo.model.Cargo;
import com.example.demo.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public List<Cargo> getAllCargos() {
        return cargoRepository.findAll();
    }

    public Optional<Cargo> getCargoById(Long id) {
        return cargoRepository.findById(id);
    }

    public Cargo createCargo(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public Cargo updateCargo(Long id, String nome) {
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cargo not found with id " + id));

        cargo.setNome(nome);
        
        return cargoRepository.save(cargo);
    }

    public void deleteCargo(Long id) {
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cargo not found with id " + id));
        cargoRepository.delete(cargo);
    }
}
