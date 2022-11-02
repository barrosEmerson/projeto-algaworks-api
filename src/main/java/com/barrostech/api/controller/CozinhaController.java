package com.barrostech.api.controller;

import java.util.List;

import com.barrostech.api.model.converter.CozinhaDTOConverter;
import com.barrostech.api.model.converter.CozinhaDTOtoCozinhaDomain;
import com.barrostech.api.dto.CozinhaDTO;
import com.barrostech.api.input.CozinhaDTOInput;
import com.barrostech.domain.services.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	public Page<CozinhaDTO> listar(Pageable pageable){
		Page<Cozinha> cozinhaPage = cozinhaRepository.findAll(pageable);
		List<CozinhaDTO> cozinhaDTO = cozinhaDTOConverter.getListCozinhaDTO(cozinhaPage.getContent());
		Page<CozinhaDTO>cozinhaDTOPage = new PageImpl<>(cozinhaDTO,pageable,cozinhaPage.getTotalElements());

		return cozinhaDTOPage;
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
