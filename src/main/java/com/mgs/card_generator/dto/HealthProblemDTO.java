package com.mgs.card_generator.dto;

import com.mgs.card_generator.model.HealthProblem;

import lombok.Getter;

@Getter
public class HealthProblemDTO {
  private Integer id;
  private String nome;
  private String descricao;

  public HealthProblemDTO(HealthProblem healthProblem) {
    this.id = healthProblem.getId();
    this.nome = healthProblem.getName();
    this.descricao = healthProblem.getDescription();
  }
}
