package com.mgs.card_generator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgs.card_generator.dto.EmployeeDTO;
import com.mgs.card_generator.dto.EmployeeInput;
import com.mgs.card_generator.model.Employee;
import com.mgs.card_generator.repository.EmployeeRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Funcionários")
@RequestMapping(path = "/funcionario")
public class EmployeeController {
  @Autowired
  private EmployeeRepository employeeRepository;

  @GetMapping("/{id}")
  @Operation(summary = "Retorna um funcionario pelo ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<EmployeeDTO> getEmployee(
      @Parameter(description = "Id a ser buscado") @PathVariable Integer id) {
    Employee existentEmployee = employeeRepository.findOrThrow(id, "Funcionário não encontrado");
    return ResponseEntity.ok(new EmployeeDTO(existentEmployee));
  }

  @PostMapping()
  @Operation(summary = "Adiciona um novo funcionário")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> addNewEmployee(
      @Parameter(description = "Dados do novo funcionário") @RequestBody @Valid EmployeeInput employee) {
    employeeRepository.save(new Employee(employee));
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  @Operation(summary = "Altera um funcionário pelo ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> editEmployee(@Parameter(description = "Id do funcionário") @PathVariable Integer id,
      @Parameter(description = "Dados para atualizar o funcionário") @RequestBody @Valid EmployeeInput newEmployeeData) {
    Employee existentEmployee = employeeRepository.findOrThrow(id, "Funcionário não encontrado");
    Employee changedEmployee = new Employee(newEmployeeData);
    changedEmployee.setId(existentEmployee.getId());
    employeeRepository.save(changedEmployee);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deleta um funcionário pelo ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> deleteEmployee(@Parameter(description = "Id do funcionário") @PathVariable Integer id) {
    Employee existentEmployee = employeeRepository.findOrThrow(id, "Funcionário não encontrado");
    employeeRepository.delete(existentEmployee);
    return ResponseEntity.ok().build();
  }

  @GetMapping()
  @Operation(summary = "Retorna todos os funcionários cadastrados")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucesso", content = {
      @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EmployeeDTO.class))) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content) })
  public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
    List<EmployeeDTO> allEmployees = new ArrayList<>();
    employeeRepository.findAll().forEach(employee -> {
      allEmployees.add(new EmployeeDTO(employee));
    });
    return ResponseEntity.ok(allEmployees);
  }
}