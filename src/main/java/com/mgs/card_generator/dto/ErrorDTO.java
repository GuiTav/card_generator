package com.mgs.card_generator.dto;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
public class ErrorDTO {
    private Integer codigo;
    private String mensagem;
    private Map<String, Object> argumentosPassados;
    
    public ErrorDTO(HttpStatus code, String message, Map<String, Object> value) {
        this.codigo = code.value();
        this.mensagem = message;
        this.argumentosPassados = value;
    }

    public ErrorDTO(HttpStatus code, String message) {
        this.codigo = code.value();
        this.mensagem = message;
    }

    @Override
    public String toString() {
        Field[] classAttributes = this.getClass().getDeclaredFields();
        Map<String, Object> validAttributes = new HashMap<>();
        for (Field field : classAttributes) {
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                if (value != null) {
                    validAttributes.put(field.getName(), value);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        try {
            return new ObjectMapper().writeValueAsString(validAttributes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
