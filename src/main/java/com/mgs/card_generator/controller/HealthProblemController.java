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

import com.mgs.card_generator.dto.ErrorDTO;
import com.mgs.card_generator.dto.HealthProblemDTO;
import com.mgs.card_generator.model.Employee;
import com.mgs.card_generator.model.HealthProblem;
import com.mgs.card_generator.repository.EmployeeRepository;
import com.mgs.card_generator.repository.HealthProblemRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "/problema")
public class HealthProblemController {
  @Autowired
  private HealthProblemRepository healthProblemRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @GetMapping("/{id}")
  public ResponseEntity<HealthProblemDTO> getHealthProblem(@PathVariable Integer id) {
    HealthProblem existentHealthProblem = healthProblemRepository.findOrThrow(id, "Problema de saúde não encontrado");
    return ResponseEntity.ok(new HealthProblemDTO(existentHealthProblem));
  }

  @PostMapping()
  public ResponseEntity<String> addNewHealthProblem(@RequestBody @Valid HealthProblem HealthProblem) {
    healthProblemRepository.save(HealthProblem);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> editHealthProblem(@PathVariable Integer id,
      @RequestBody @Valid HealthProblem newHealthProblemData) {
    HealthProblem existentHealthProblem = healthProblemRepository.findOrThrow(id, "Problema de saúde não encontrado");
    newHealthProblemData.setId(existentHealthProblem.getId());
    healthProblemRepository.save(newHealthProblemData);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteHealthProblem(@PathVariable Integer id) {
    HealthProblem existentHealthProblem = healthProblemRepository.findOrThrow(id, "Problema de saúde não encontrado");
    healthProblemRepository.delete(existentHealthProblem);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{healthProblemId}/funcionario/{employeeId}")
  public ResponseEntity<String> attributeHealthProblemToEmployee(@PathVariable Integer healthProblemId,
      @PathVariable Integer employeeId) {
    HealthProblem existentHealthProblem = healthProblemRepository.findOrThrow(healthProblemId, "Problema de saúde não encontrado");
    Employee existentEmployee = employeeRepository.findOrThrow(employeeId, "Funcionario não encontrado");

    existentHealthProblem.getEmployees().add(existentEmployee);
    healthProblemRepository.save(existentHealthProblem);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{healthProblemId}/funcionario/{employeeId}")
  public ResponseEntity<String> removeHealthProblemFromEmployee(@PathVariable Integer healthProblemId,
      @PathVariable Integer employeeId) {
    HealthProblem existentHealthProblem = healthProblemRepository.findOrThrow(healthProblemId, "Problema de saúde não encontrado");
    Employee existentEmployee = employeeRepository.findOrThrow(employeeId, "Funcionario não encontrado");

    if (!existentHealthProblem.getEmployees().contains(existentEmployee)) {
      ErrorDTO errorMessage = new ErrorDTO(
        HttpStatus.NOT_FOUND,
        "Funcionario não possui este problema de saude",
        Map.of(
          "idProblema", healthProblemId,
          "idFuncionario", employeeId));
      throw new NoSuchElementException(errorMessage.toString());
    }
    existentHealthProblem.getEmployees().remove(existentEmployee);
    healthProblemRepository.save(existentHealthProblem);
    return ResponseEntity.ok().build();
  }

  @GetMapping()
  public ResponseEntity<List<HealthProblemDTO>> getAllHealthProblems() {
    List<HealthProblemDTO> allHealthProblems = new ArrayList<>();
    healthProblemRepository.findAll().forEach(HealthProblem -> {
      allHealthProblems.add(new HealthProblemDTO(HealthProblem));
    });
    return ResponseEntity.ok(allHealthProblems);
  }
}