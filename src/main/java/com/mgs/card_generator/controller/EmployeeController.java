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

import com.mgs.card_generator.dto.EmployeeDTO;
import com.mgs.card_generator.model.Employee;
import com.mgs.card_generator.repository.EmployeeRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "/funcionario")
public class EmployeeController {
  @Autowired
  private EmployeeRepository employeeRepository;

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Integer id) {
    Employee existentEmployee = employeeRepository.findOrThrow(id, "Funcionário não encontrado");
    return ResponseEntity.ok(new EmployeeDTO(existentEmployee));
  }

  @PostMapping()
  public ResponseEntity<String> addNewEmployee(@RequestBody @Valid Employee employee) {
    employeeRepository.save(employee);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> editEmployee(@PathVariable Integer id, @RequestBody @Valid Employee newEmployeeData) {
    Employee existentEmployee = employeeRepository.findOrThrow(id, "Funcionário não encontrado");
    newEmployeeData.setId(existentEmployee.getId());
    employeeRepository.save(newEmployeeData);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {
    Employee existentEmployee = employeeRepository.findOrThrow(id, "Funcionário não encontrado");
    employeeRepository.delete(existentEmployee);
    return ResponseEntity.ok().build();
  }

  @GetMapping()
  public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
    List<EmployeeDTO> allEmployees = new ArrayList<>();
    employeeRepository.findAll().forEach(employee -> {
      allEmployees.add(new EmployeeDTO(employee));
    });
    return ResponseEntity.ok(allEmployees);
  }
}