package com.barrostech.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.barrostech.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> consultarPorNome(String nome, Long cozinha);

}
