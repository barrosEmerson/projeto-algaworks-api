package com.barrostech.domain.repository;

import java.util.List;

import com.barrostech.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {
	
	List<Cozinha> findByNomeContaining(String nome);

}
