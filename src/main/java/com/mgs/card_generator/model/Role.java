package com.mgs.card_generator.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role extends GeneralModel{
  @NotBlank
  private String name;

  public Role() {
    super();
  }

  public Role(Integer id) {
    super.setId(id);
  }
}