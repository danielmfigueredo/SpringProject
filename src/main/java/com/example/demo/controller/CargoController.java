package com.example.demo.controller;


import com.example.demo.model.Cargo;
import com.example.demo.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @GetMapping
    public List<Cargo> getAllCargos() {
        return cargoService.getAllCargos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cargo> getCargoById(@PathVariable Long id) {
        return cargoService.getCargoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cargo> createCargo(@RequestParam String nome) {
        Cargo cargo = new Cargo();
        cargo.setNome(nome);
        Cargo novoCargo = cargoService.createCargo(cargo);
        return ResponseEntity.ok(novoCargo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cargo> updateCargo(@PathVariable Long id, @RequestBody Cargo cargoDetails) {
        return ResponseEntity.ok(cargoService.updateCargo(id, cargoDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Long id) {
        cargoService.deleteCargo(id);
        return ResponseEntity.noContent().build();
    }
}
