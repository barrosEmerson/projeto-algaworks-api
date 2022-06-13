package com.barrostech.api.controller;

import java.util.List;
import java.util.Optional;

import com.barrostech.api.converter.CozinhaDTOConverter;
import com.barrostech.api.converter.CozinhaDTOtoCozinhaDomain;
import com.barrostech.api.dto.CozinhaDTO;
import com.barrostech.api.input.CozinhaDTOInput;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Autowired
	private CozinhaDTOConverter cozinhaDTOConverter;

	@Autowired
	private CozinhaDTOtoCozinhaDomain cozinhaDomain;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CozinhaDTO> listar(){

		return cozinhaDTOConverter.getListCozinhaDTO(cozinhaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public CozinhaDTO buscarPorId(@PathVariable Long id) {

		Cozinha cozinha =  cadastroCozinhaService.buscarOuFalhar(id);
		CozinhaDTO dto = cozinhaDTOConverter.getCozinhaDTO(cozinha);

		  return dto;

	}
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO salvar(@RequestBody @Valid CozinhaDTOInput cozinhaInput){
		Cozinha cozinha = cozinhaDomain.objectToDomain(cozinhaInput);
		return cozinhaDTOConverter.getCozinhaDTO(cadastroCozinhaService.salvar(cozinha));
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaDTOInput cozinhaDTOInput){
		 Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		 cozinhaDomain.copyToDomainObject(cozinhaDTOInput,cozinhaAtual);
		 return cozinhaDTOConverter.getCozinhaDTO(cadastroCozinhaService.salvar(cozinhaAtual));
//			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

	}


	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){
			cadastroCozinhaService.excluir(cozinhaId);
	}
}
