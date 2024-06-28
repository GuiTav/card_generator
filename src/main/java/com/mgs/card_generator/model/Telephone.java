package com.mgs.card_generator.model;

import com.mgs.card_generator.dto.TelephoneInput;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Telephone extends GeneralModel{
    @NotBlank
    private String ddi;

    @NotBlank
    private String ddd;

    @NotBlank
    private String number;

    @ManyToOne
    @NotNull
    private Employee owner;

    public Telephone() {
        super();
    }

    public Telephone(Integer id) {
        super.setId(id);
    }

    public Telephone(TelephoneInput data) {
        this.ddi = data.getDdi();
        this.ddd = data.getDdd();
        this.number = data.getNumero();
        this.owner = new Employee(data.getIdDono());
    }
}
