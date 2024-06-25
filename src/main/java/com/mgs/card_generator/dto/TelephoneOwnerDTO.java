package com.mgs.card_generator.dto;

import com.mgs.card_generator.model.Telephone;

import lombok.Getter;

@Getter
public class TelephoneOwnerDTO {
    private String telefone;

    public TelephoneOwnerDTO(Telephone telephone) {
        this.telefone = String.format("+%s (%s) %s", telephone.getDdi(), telephone.getDdd(), telephone.getNumber());
    }
}
