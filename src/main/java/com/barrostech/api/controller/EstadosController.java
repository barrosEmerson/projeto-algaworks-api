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
	public ResponseEntity<Estado> buscar(@PathVariable Long estadoId){
		Optional<Estado> estado = estadoRepository.findById(estadoId);

		if(estado.isPresent()){
			return ResponseEntity.ok(estado.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{estadoId}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado){
		Estado estadoAtual = estadoRepository.findById(estadoId).orElse(null);

		if(estadoAtual != null){
			BeanUtils.copyProperties(estado, estadoAtual,"id");

			Estado estadoSalvo = cadastroEstadoService.salvar(estadoAtual);
			return ResponseEntity.ok(estadoSalvo);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> remover(@PathVariable Long estadoId){
		try {
			cadastroEstadoService.excluir(estadoId);
			return ResponseEntity.noContent().build();
		}catch (EntidadeEmUsoException e){
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	@PostMapping
	public Estado adicionar(@RequestBody Estado estado){
		return cadastroEstadoService.salvar(estado);
	}
}
