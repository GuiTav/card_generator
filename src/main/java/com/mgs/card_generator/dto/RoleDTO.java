package com.mgs.card_generator.dto;

import com.mgs.card_generator.model.Role;

import lombok.Getter;

@Getter
public class RoleDTO {
    private Integer id;
    private String nome;

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.nome = role.getName();
    }
}
