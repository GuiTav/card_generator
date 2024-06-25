package com.mgs.card_generator.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mgs.card_generator.dto.AllergyDTO;
import com.mgs.card_generator.dto.ErrorDTO;
import com.mgs.card_generator.model.Allergy;
import com.mgs.card_generator.model.Employee;
import com.mgs.card_generator.repository.AllergyRepository;
import com.mgs.card_generator.repository.EmployeeRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "/alergia")
public class AllergyController {
  
  @Autowired
  private AllergyRepository allergyRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @GetMapping("/{id}")
  public ResponseEntity<AllergyDTO> getAllergy(@PathVariable Integer id) {
    Allergy existentAllergy = allergyRepository.findOrThrow(id, "Alergia não encontrada");
    return ResponseEntity.ok(new AllergyDTO(existentAllergy));
  }

  @PostMapping()
  public ResponseEntity<String> addNewAllergy(@RequestBody @Valid Allergy allergy) {
    allergyRepository.save(allergy);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> editAllergy(@PathVariable Integer id, @RequestBody @Valid Allergy newAllergyData) {
    Allergy existentAllergy = allergyRepository.findOrThrow(id, "Alergia não encontrada");
    newAllergyData.setId(existentAllergy.getId());
    allergyRepository.save(newAllergyData);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteAllergy(@PathVariable Integer id) {
    Allergy existentAllergy = allergyRepository.findOrThrow(id, "Alergia não encontrada");
    allergyRepository.delete(existentAllergy);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{allergyId}/funcionario/{employeeId}")
  public ResponseEntity<String> attributeAllergyToEmployee(@PathVariable Integer allergyId,
      @PathVariable Integer employeeId) {
    Allergy existentAllergy = allergyRepository.findOrThrow(allergyId, "Alergia não encontrada");
    Employee existentEmployee = employeeRepository.findOrThrow(employeeId, "Funcionario não encontrado");

    existentAllergy.getEmployees().add(existentEmployee);
    allergyRepository.save(existentAllergy);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{allergyId}/funcionario/{employeeId}")
  public ResponseEntity<String> removeAllergyFromEmployee(@PathVariable Integer allergyId,
      @PathVariable Integer employeeId) {
    Allergy existentAllergy = allergyRepository.findOrThrow(allergyId, "Alergia não encontrada");
    Employee existentEmployee = employeeRepository.findOrThrow(employeeId, "Funcionario não encontrado");

    if (!existentAllergy.getEmployees().contains(existentEmployee)) {
      ErrorDTO errorMessage = new ErrorDTO(
        HttpStatus.NOT_FOUND,
        "Funcionario não possui esta alergia",
        Map.of(
          "idAlergia", allergyId,
          "idFuncionario", employeeId));
      throw new NoSuchElementException(errorMessage.toString());
    }
    existentAllergy.getEmployees().remove(existentEmployee);
    allergyRepository.save(existentAllergy);
    return ResponseEntity.ok().build();
  }

  @GetMapping()
  public ResponseEntity<List<AllergyDTO>> getAllAllergys() {
    List<AllergyDTO> allAllergies = new ArrayList<>();
    allergyRepository.findAll().forEach(allergy -> {
      allAllergies.add(new AllergyDTO(allergy));
    });
    return ResponseEntity.ok(allAllergies);
  }
}