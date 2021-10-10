package com.barrostech.api.controller;

import java.math.BigDecimal;
import java.util.List;

import com.barrostech.domain.model.Restaurante;
import com.barrostech.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.barrostech.domain.model.Cozinha;
import com.barrostech.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;
	

	@GetMapping("cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(@RequestParam String nome){
		return cozinhaRepository.findByNomeContaining(nome);
	}

	@GetMapping("restaurante/por-taxa-frete")
	public List<Restaurante> restaurantePorTaxaFrete(@RequestParam BigDecimal taxaInicial, @RequestParam BigDecimal taxaFinal){
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial,taxaFinal);
	}

	@GetMapping("restaurante/por-nome")
	public List<Restaurante> restaurantePorNome(String nome, Long cozinha){
		return restauranteRepository.consultarPorNome(nome, cozinha);
	}
}
