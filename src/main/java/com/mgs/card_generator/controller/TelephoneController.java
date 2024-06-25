package com.mgs.card_generator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mgs.card_generator.dto.TelephoneDTO;
import com.mgs.card_generator.model.Telephone;
import com.mgs.card_generator.repository.TelephoneRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "/telefone")
public class TelephoneController {

    @Autowired
    TelephoneRepository telephoneRepository;

    @GetMapping("/{id}")
    public ResponseEntity<TelephoneDTO> getTelephone(@PathVariable Integer id) {
        Telephone existentTelephone = telephoneRepository.findOrThrow(id, "Telefone não encontrado");
        return ResponseEntity.ok(new TelephoneDTO(existentTelephone));
    }

    @PostMapping()
    public ResponseEntity<String> addNewTelephone(@RequestBody @Valid Telephone telephone) {
        telephoneRepository.save(telephone);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editTelephone(@PathVariable Integer id, @RequestBody @Valid Telephone newTelephoneData) {
        Telephone existentTelephone = telephoneRepository.findOrThrow(id, "Telefone não encontrado");
        newTelephoneData.setId(existentTelephone.getId());
        telephoneRepository.save(newTelephoneData);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTelephone(@PathVariable Integer id) {
        Telephone existentTelephone = telephoneRepository.findOrThrow(id, "Telefone não encontrado");
        telephoneRepository.delete(existentTelephone);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<TelephoneDTO>> getAllTelephones() {
        List<TelephoneDTO> allTelephones = new ArrayList<>();
        telephoneRepository.findAll().forEach(Telephone -> {
            allTelephones.add(new TelephoneDTO(Telephone));
        });
        return ResponseEntity.ok(allTelephones);
    }

}
