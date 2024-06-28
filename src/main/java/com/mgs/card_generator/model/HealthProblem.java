package com.mgs.card_generator.model;

import java.util.Set;

import com.mgs.card_generator.dto.HealthProblemInput;

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
public class HealthProblem extends GeneralModel{
  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "problem_employee", joinColumns = @JoinColumn(name = "problem_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
  Set<Employee> employees;

  public HealthProblem() {
    super();
  }

  public HealthProblem(Integer id) {
    super.setId(id);
  }

  public HealthProblem(HealthProblemInput data) {
    this.name = data.getNome();
    this.description = data.getDescricao();
  }
}