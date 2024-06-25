package com.mgs.card_generator.dto;

import com.mgs.card_generator.model.Telephone;

import lombok.Getter;

@Getter
public class TelephoneDTO {
    private Integer id;
    private String telefone;
    private Integer dono;

    public TelephoneDTO(Telephone telephone) {
        this.id = telephone.getId();
        this.telefone = String.format("+%s (%s) %s", telephone.getDdi(), telephone.getDdd(), telephone.getNumber());
        this.dono = telephone.getOwner().getId();
    }
}
