package com.barrostech.api.controller;

import java.util.List;

import com.barrostech.api.model.converter.EstadoDTOConverter;
import com.barrostech.api.model.converter.EstadoDTOtoEstadoDomain;
import com.barrostech.api.dto.EstadoDTO;
import com.barrostech.api.input.EstadoDTOInput;
import com.barrostech.domain.services.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.barrostech.domain.model.Estado;
import com.barrostech.domain.repository.EstadoRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/estados")
public class EstadosController {

	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	@Autowired
	private EstadoDTOConverter estadoDTOConverter;
	@Autowired
	private EstadoDTOtoEstadoDomain estadoDomain;
	
	@GetMapping
	public List<EstadoDTO> listar(){

		return estadoDTOConverter.getListEstadoDTO(estadoRepository.findAll());
	}

	@GetMapping("/{estadoId}")
	public EstadoDTO buscar(@PathVariable Long estadoId){
		return estadoDTOConverter.getEstadoDTO(cadastroEstadoService.buscarOuFalhar(estadoId));

	}

	@PutMapping("/{estadoId}")
	public EstadoDTO atualizar(@PathVariable Long estadoId, @RequestBody EstadoDTOInput estadoInput){

		Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(estadoId);
		estadoDomain.copyToDomainObject(estadoInput, estadoAtual);

			return estadoDTOConverter.getEstadoDTO(cadastroEstadoService.salvar(estadoAtual));

	}

	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable Long estadoId){
		cadastroEstadoService.excluir(estadoId);
	}
	@PostMapping
	public EstadoDTO adicionar(@RequestBody @Valid EstadoDTOInput estadoDTOInput){
		Estado estado = estadoDomain.dtoToDomain(estadoDTOInput);
		return estadoDTOConverter.getEstadoDTO(cadastroEstadoService.salvar(estado));
	}
}
