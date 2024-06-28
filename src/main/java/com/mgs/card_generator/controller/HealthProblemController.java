package com.mgs.card_generator.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgs.card_generator.dto.ErrorDTO;
import com.mgs.card_generator.dto.HealthProblemDTO;
import com.mgs.card_generator.dto.HealthProblemInput;
import com.mgs.card_generator.model.Employee;
import com.mgs.card_generator.model.HealthProblem;
import com.mgs.card_generator.repository.EmployeeRepository;
import com.mgs.card_generator.repository.HealthProblemRepository;

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
@Tag(name = "Problemas de saúde")
@RequestMapping(path = "/problema")
public class HealthProblemController {
  @Autowired
  private HealthProblemRepository healthProblemRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @GetMapping("/{id}")
  @Operation(summary = "Retorna um problema de saúde pelo ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = HealthProblemDTO.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<HealthProblemDTO> getHealthProblem(
      @Parameter(description = "Id a ser buscado") @PathVariable Integer id) {
    HealthProblem existentHealthProblem = healthProblemRepository.findOrThrow(id, "Problema de saúde não encontrado");
    return ResponseEntity.ok(new HealthProblemDTO(existentHealthProblem));
  }

  @PostMapping()
  @Operation(summary = "Adiciona um novo problema de saúde")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> addNewHealthProblem(
      @Parameter(description = "Dados do novo problema de saúde") @RequestBody @Valid HealthProblemInput HealthProblem) {
    healthProblemRepository.save(new HealthProblem(HealthProblem));
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  @Operation(summary = "Altera um problema de saúde pelo ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> editHealthProblem(
      @Parameter(description = "Id do problema de saúde") @PathVariable Integer id,
      @Parameter(description = "Dados para atualizar o problema de saúde") @RequestBody @Valid HealthProblemInput newHealthProblemData) {
    HealthProblem existentHealthProblem = healthProblemRepository.findOrThrow(id, "Problema de saúde não encontrado");
    HealthProblem changedHealthProblem = new HealthProblem(newHealthProblemData);
    changedHealthProblem.setId(existentHealthProblem.getId());
    healthProblemRepository.save(changedHealthProblem);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deleta um problema de saúde pelo ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> deleteHealthProblem(
      @Parameter(description = "Id do problema de saúde") @PathVariable Integer id) {
    HealthProblem existentHealthProblem = healthProblemRepository.findOrThrow(id, "Problema de saúde não encontrado");
    healthProblemRepository.delete(existentHealthProblem);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{healthProblemId}/funcionario/{employeeId}")
  @Operation(summary = "Adiciona um problema de saúde a um funcionário")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> attributeHealthProblemToEmployee(
      @Parameter(description = "Id do problema de saúde") @PathVariable Integer healthProblemId,
      @Parameter(description = "Id do funcionário") @PathVariable Integer employeeId) {
    HealthProblem existentHealthProblem = healthProblemRepository.findOrThrow(healthProblemId,
        "Problema de saúde não encontrado");
    Employee existentEmployee = employeeRepository.findOrThrow(employeeId, "Funcionario não encontrado");

    existentHealthProblem.getEmployees().add(existentEmployee);
    healthProblemRepository.save(existentHealthProblem);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{healthProblemId}/funcionario/{employeeId}")
  @Operation(summary = "Remove um problema de saúde de um funcionário")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> removeHealthProblemFromEmployee(
      @Parameter(description = "Id do problema de saúde") @PathVariable Integer healthProblemId,
      @Parameter(description = "Id do funcionário") @PathVariable Integer employeeId) {
    HealthProblem existentHealthProblem = healthProblemRepository.findOrThrow(healthProblemId,
        "Problema de saúde não encontrado");
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
  @Operation(summary = "Retorna todos os problemas de saúde cadastradas")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucesso", content = {
      @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = HealthProblemDTO.class))) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content) })
  public ResponseEntity<List<HealthProblemDTO>> getAllHealthProblems() {
    List<HealthProblemDTO> allHealthProblems = new ArrayList<>();
    healthProblemRepository.findAll().forEach(HealthProblem -> {
      allHealthProblems.add(new HealthProblemDTO(HealthProblem));
    });
    return ResponseEntity.ok(allHealthProblems);
  }
}