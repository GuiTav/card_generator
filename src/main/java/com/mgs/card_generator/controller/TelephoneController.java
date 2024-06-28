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

import com.mgs.card_generator.dto.TelephoneDTO;
import com.mgs.card_generator.dto.TelephoneInput;
import com.mgs.card_generator.model.Telephone;
import com.mgs.card_generator.repository.TelephoneRepository;

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
@Tag(name = "Telefones")
@RequestMapping(path = "/telefone")
public class TelephoneController {

    @Autowired
    TelephoneRepository telephoneRepository;

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um telefone pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TelephoneDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
    public ResponseEntity<TelephoneDTO> getTelephone(
            @Parameter(description = "Id a ser buscado") @PathVariable Integer id) {
        Telephone existentTelephone = telephoneRepository.findOrThrow(id, "Telefone não encontrado");
        return ResponseEntity.ok(new TelephoneDTO(existentTelephone));
    }

    @PostMapping()
    @Operation(summary = "Adiciona um novo telefone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
            @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
    public ResponseEntity<String> addNewTelephone(
            @Parameter(description = "Dados do novo funcionário") @RequestBody @Valid TelephoneInput telephone) {
        telephoneRepository.save(new Telephone(telephone));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Altera um telefone pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
            @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
    public ResponseEntity<String> editTelephone(@Parameter(description = "Id do telefone") @PathVariable Integer id,
            @Parameter(description = "Dados para atualizar o telefone") @RequestBody @Valid TelephoneInput newTelephoneData) {
        Telephone existentTelephone = telephoneRepository.findOrThrow(id, "Telefone não encontrado");
        Telephone changedTelephone = new Telephone(newTelephoneData);
        changedTelephone.setId(existentTelephone.getId());
        telephoneRepository.save(changedTelephone);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um telefone pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
            @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Id não encontrado", content = @Content) })
    public ResponseEntity<String> deleteTelephone(@Parameter(description = "Id do telefone") @PathVariable Integer id) {
        Telephone existentTelephone = telephoneRepository.findOrThrow(id, "Telefone não encontrado");
        telephoneRepository.delete(existentTelephone);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    @Operation(summary = "Retorna todos os telefones cadastrados")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TelephoneDTO.class))) }),
            @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content) })
    public ResponseEntity<List<TelephoneDTO>> getAllTelephones() {
        List<TelephoneDTO> allTelephones = new ArrayList<>();
        telephoneRepository.findAll().forEach(Telephone -> {
            allTelephones.add(new TelephoneDTO(Telephone));
        });
        return ResponseEntity.ok(allTelephones);
    }

}
