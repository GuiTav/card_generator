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

import com.mgs.card_generator.dto.RoleDTO;
import com.mgs.card_generator.dto.RoleInput;
import com.mgs.card_generator.model.Role;
import com.mgs.card_generator.repository.RoleRepository;

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
@Tag(name = "Cargos")
@RequestMapping(path = "/cargo")
public class RoleController {
  @Autowired
  private RoleRepository roleRepository;

  @GetMapping("/{id}")
  @Operation(summary = "Retorna um cargo pelo ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = RoleDTO.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<RoleDTO> getRole(@Parameter(description = "Id a ser buscado") @PathVariable Integer id) {
    Role existentRole = roleRepository.findOrThrow(id, "Cargo não encontrado");
    return ResponseEntity.ok(new RoleDTO(existentRole));
  }

  @PostMapping()
  @Operation(summary = "Adiciona um novo cargo")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> addNewRole(
      @Parameter(description = "Dados do novo cargo") @RequestBody @Valid RoleInput role) {
    roleRepository.save(new Role(role));
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  @Operation(summary = "Altera um cargo pelo ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> editRole(@Parameter(description = "Id do cargo") @PathVariable Integer id,
      @Parameter(description = "Dados para atualizar o cargo") @RequestBody @Valid RoleInput newRoleData) {
    Role existentRole = roleRepository.findOrThrow(id, "Cargo não encontrado");
    Role changedRole = new Role(newRoleData);
    changedRole.setId(existentRole.getId());
    roleRepository.save(changedRole);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deleta um cargo pelo ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
  public ResponseEntity<String> deleteRole(@Parameter(description = "Id do cargo") @PathVariable Integer id) {
    Role existentRole = roleRepository.findOrThrow(id, "Cargo não encontrado");
    roleRepository.delete(existentRole);
    return ResponseEntity.ok().build();
  }

  @GetMapping()
  @Operation(summary = "Retorna todos os cargos cadastrados")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucesso", content = {
      @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RoleDTO.class))) }),
      @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content) })
  public ResponseEntity<List<RoleDTO>> getAllRoles() {
    List<RoleDTO> allRoles = new ArrayList<>();
    roleRepository.findAll().forEach(role -> {
      allRoles.add(new RoleDTO(role));
    });
    return ResponseEntity.ok(allRoles);
  }
}
