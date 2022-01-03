package com.barrostech.api.controller;

import java.util.List;
import java.util.Optional;

import com.barrostech.domain.exception.EntidadeEmUsoException;
import com.barrostech.domain.services.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.barrostech.domain.model.Estado;
import com.barrostech.domain.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadosController {

	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@GetMapping
	public List<Estado> listar(){
		return estadoRepository.findAll();
	}

	@GetMapping("/{estadoId}")
	public Estado buscar(@PathVariable Long estadoId){
		return cadastroEstadoService.buscarOuFalhar(estadoId);

	}

	@PutMapping("/{estadoId}")
	public Estado atualizar(@PathVariable Long estadoId, @RequestBody Estado estado){
		Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(estadoId);


			BeanUtils.copyProperties(estado, estadoAtual,"id");

			return cadastroEstadoService.salvar(estadoAtual);



	}

	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable Long estadoId){
		cadastroEstadoService.excluir(estadoId);
	}
	@PostMapping
	public Estado adicionar(@RequestBody Estado estado){
		return cadastroEstadoService.salvar(estado);
	}
}
