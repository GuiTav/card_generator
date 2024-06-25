package com.mgs.card_generator.dto;

import com.mgs.card_generator.model.HealthProblem;

import lombok.Getter;

@Getter
public class ProblemDescriptionDTO {
    private String nome;
    private String descricao;

    public ProblemDescriptionDTO(HealthProblem healthProblem) {
        this.nome = healthProblem.getName();
        this.descricao = healthProblem.getDescription();
    }
}
