package com.barrostech.api.controller;

import java.util.List;
import java.util.Optional;

import com.barrostech.domain.exception.EntidadeEmUsoException;
import com.barrostech.domain.exception.EntidadeNaoEncontradaException;
import com.barrostech.domain.services.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.barrostech.domain.model.Cozinha;
import com.barrostech.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cozinha> listar(){
		return cozinhaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscarPorId(@PathVariable Long id) {
		 Optional<Cozinha> cozinha = cozinhaRepository.findById(id);

		 if(cozinha.isPresent()){

			 return ResponseEntity.ok(cozinha.get());
		 }
		 return ResponseEntity.notFound().build();
	}
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha salvar(@RequestBody Cozinha cozinha){
		return cadastroCozinhaService.salvar(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha){
		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);

		if(cozinhaAtual.isPresent()){
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");

			Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual.get());
			return ResponseEntity.ok(cozinhaSalva);
		}
		return ResponseEntity.notFound().build();
	}

	/*@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<?> remover(@PathVariable Long cozinhaId){
		try {
			cadastroCozinhaService.excluir(cozinhaId);
			return ResponseEntity.noContent().build();
		*//*}catch (EntidadeNaoEncontradaException e){
				return ResponseEntity.notFound().build();*//*
		}catch (EntidadeEmUsoException e){
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}*/

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){
			cadastroCozinhaService.excluir(cozinhaId);
	}
}
