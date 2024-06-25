package com.mgs.card_generator.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee extends GeneralModel{
  @NotBlank
  private String name;
  
  @NotBlank
  private String email;

  @NotNull
  @ManyToOne
  private Role role;

  @OneToMany(mappedBy = "owner")
  private Set<Telephone> telephoneNumbers;

  @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
  Set<Allergy> allergies;

  @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
  Set<HealthProblem> healthProblems;

  public Employee() {
    super();
  }

  public Employee(Integer id) {
    super.setId(id);
  }
}