package com.barrostech.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.barrostech.domain.model.Restaurante;
import com.barrostech.infrastructure.RestauranteRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries {

    @Query("from Restaurante r join r.cozinha left join fetch r.formasPagamento ")
    List<Restaurante> findAll();

    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> consultarPorNome(String nome, Long cozinha);

    @Query("from Restaurante where nome like %:nome% and taxaFrete between :taxaInicial and :taxaFinal")
    List<Restaurante> testando(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

}
