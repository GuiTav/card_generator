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

import com.mgs.card_generator.dto.AllergyDTO;
import com.mgs.card_generator.dto.AllergyInput;
import com.mgs.card_generator.dto.ErrorDTO;
import com.mgs.card_generator.model.Allergy;
import com.mgs.card_generator.model.Employee;
import com.mgs.card_generator.repository.AllergyRepository;
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
@Tag(name = "Alergias")
@RequestMapping("/alergia")
public class AllergyController {

  @Autowired
  private AllergyRepository allergyRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @GetMapping("/{id}")
  @Operation(summary = "Retorna uma alergia pelo ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = AllergyDTO.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<AllergyDTO> getAllergy(@Parameter(description = "Id a ser buscado") @PathVariable Integer id) {
    Allergy existentAllergy = allergyRepository.findOrThrow(id, "Alergia não encontrada");
    return ResponseEntity.ok(new AllergyDTO(existentAllergy));
  }

  @PostMapping()
  @Operation(summary = "Adiciona uma nova alergia")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> addNewAllergy(
      @Parameter(description = "Dados da nova alergia") @RequestBody @Valid AllergyInput allergy) {
    allergyRepository.save(new Allergy(allergy));
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  @Operation(summary = "Altera uma alergia pelo ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> editAllergy(@Parameter(description = "Id da alergia") @PathVariable Integer id,
      @Parameter(description = "Dados para atualizar a alergia") @RequestBody @Valid AllergyInput newAllergyData) {
    Allergy existentAllergy = allergyRepository.findOrThrow(id, "Alergia não encontrada");
    Allergy changedAllergy = new Allergy(newAllergyData);
    changedAllergy.setId(existentAllergy.getId());
    allergyRepository.save(changedAllergy);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deleta uma alergia pelo ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> deleteAllergy(@Parameter(description = "Id da alergia") @PathVariable Integer id) {
    Allergy existentAllergy = allergyRepository.findOrThrow(id, "Alergia não encontrada");
    allergyRepository.delete(existentAllergy);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{allergyId}/funcionario/{employeeId}")
  @Operation(summary = "Adiciona uma alergia a um funcionário")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> attributeAllergyToEmployee(
      @Parameter(description = "Id da alergia") @PathVariable Integer allergyId,
      @Parameter(description = "Id do funcionário") @PathVariable Integer employeeId) {
    Allergy existentAllergy = allergyRepository.findOrThrow(allergyId, "Alergia não encontrada");
    Employee existentEmployee = employeeRepository.findOrThrow(employeeId, "Funcionario não encontrado");

    existentAllergy.getEmployees().add(existentEmployee);
    allergyRepository.save(existentAllergy);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{allergyId}/funcionario/{employeeId}")
  @Operation(summary = "Remove uma alergia de um funcionário")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> removeAllergyFromEmployee(
      @Parameter(description = "Id da alergia") @PathVariable Integer allergyId,
      @Parameter(description = "Id do funcionário") @PathVariable Integer employeeId) {
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
  @Operation(summary = "Retorna todas as alergias cadastradas")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucesso", content = {
      @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AllergyDTO.class))) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content) })
  public ResponseEntity<List<AllergyDTO>> getAllAllergys() {
    List<AllergyDTO> allAllergies = new ArrayList<>();
    allergyRepository.findAll().forEach(allergy -> {
      allAllergies.add(new AllergyDTO(allergy));
    });
    return ResponseEntity.ok(allAllergies);
  }
}