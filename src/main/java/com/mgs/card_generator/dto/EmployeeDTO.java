package com.mgs.card_generator.dto;

import java.util.List;

import com.mgs.card_generator.model.Employee;

import lombok.Getter;

@Getter
public class EmployeeDTO {
    private Integer id;
    private String nome;
    private String email;
    private RoleDTO cargo;
    private List<String> alergias;
    private List<ProblemDescriptionDTO> problemasDeSaude;
    private List<TelephoneOwnerDTO> telefones;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.nome = employee.getName();
        this.email = employee.getEmail();
        this.cargo = new RoleDTO(employee.getRole());
        this.alergias = employee.getAllergies().stream().map(allergy -> {
            return allergy.getAllergenic();
        }).toList();
        this.problemasDeSaude = employee.getHealthProblems().stream().map(problem -> {
            return new ProblemDescriptionDTO(problem);
        }).toList();
        this.telefones = employee.getTelephoneNumbers().stream().map(telephone -> {
            return new TelephoneOwnerDTO(telephone);
        }).toList();
    }
}
