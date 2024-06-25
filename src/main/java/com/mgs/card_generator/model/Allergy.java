package com.mgs.card_generator.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Allergy extends GeneralModel{
  @NotBlank
  private String allergenic;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "allergy_employee", joinColumns = @JoinColumn(name = "allergy_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
  Set<Employee> employees;

  public Allergy() {
    super();
  }

  public Allergy(Integer id) {
    super.setId(id);
  }
}
