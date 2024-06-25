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

import com.mgs.card_generator.dto.RoleDTO;
import com.mgs.card_generator.model.Role;
import com.mgs.card_generator.repository.RoleRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "/cargo")
public class RoleController {
  @Autowired
  private RoleRepository roleRepository;

  @GetMapping("/{id}")
  public ResponseEntity<RoleDTO> getRole(@PathVariable Integer id) {
    Role existentRole = roleRepository.findOrThrow(id, "Cargo não encontrado");
    return ResponseEntity.ok(new RoleDTO(existentRole));
  }

  @PostMapping()
  public ResponseEntity<String> addNewRole(@RequestBody @Valid Role role) {
    roleRepository.save(role);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> editRole(@PathVariable Integer id, @RequestBody @Valid Role newRoleData) {
    Role existentRole = roleRepository.findOrThrow(id, "Cargo não encontrado");
    newRoleData.setId(existentRole.getId());
    roleRepository.save(newRoleData);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteRole(@PathVariable Integer id) {
    Role existentRole = roleRepository.findOrThrow(id, "Cargo não encontrado");
    roleRepository.delete(existentRole);
    return ResponseEntity.ok().build();
  }

  @GetMapping()
  public ResponseEntity<List<RoleDTO>> getAllRoles() {
    List<RoleDTO> allRoles = new ArrayList<>();
    roleRepository.findAll().forEach(role -> {
      allRoles.add(new RoleDTO(role));
    });
    return ResponseEntity.ok(allRoles);
  }
}
