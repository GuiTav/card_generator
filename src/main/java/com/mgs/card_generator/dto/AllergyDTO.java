package com.mgs.card_generator.dto;

import com.mgs.card_generator.model.Allergy;

import lombok.Getter;

@Getter
public class AllergyDTO {
    private Integer id;
    private String alergenico;

    public AllergyDTO(Allergy allergy) {
        this.id = allergy.getId();
        this.alergenico = allergy.getAllergenic();
    }
}
