package com.mgs.card_generator.dto;

import lombok.Data;

@Data
public class TelephoneInput {
    private String ddi;
    private String ddd;
    private String numero;
    private Integer idDono;
}
