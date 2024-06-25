package com.mgs.card_generator.repository;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.HttpStatus;

import com.mgs.card_generator.dto.ErrorDTO;
import com.mgs.card_generator.model.GeneralModel;

@NoRepositoryBean
public interface GeneralRepository<T extends GeneralModel> extends JpaRepository<T, Integer> {

    public default T findOrThrow(Integer id, String throwMessage) {
        return this.findById(id).orElseThrow(() -> {
            ErrorDTO errorMessage = new ErrorDTO(
                HttpStatus.NOT_FOUND,
                throwMessage,
                Map.of("id", id));
            throw new NoSuchElementException(errorMessage.toString());
        });
    }
    
}