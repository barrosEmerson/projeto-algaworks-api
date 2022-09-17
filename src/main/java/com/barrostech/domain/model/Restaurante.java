package com.barrostech.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.barrostech.core.validation.Groups;
import com.barrostech.core.validation.Multiplo;
import com.barrostech.core.validation.TaxaFrete;
import com.barrostech.core.validation.ValorZeroIncluiDescricao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Gratis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String nome;

	@TaxaFrete
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cozinha_id",nullable = false)
	private Cozinha cozinha;

	@Embedded
	private Endereco endereco;

	private Boolean ativo = Boolean.TRUE;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>();

	private Boolean aberto = Boolean.TRUE;


	public void ativar(){
		setAtivo(true);
	}

	public void inativar(){
		setAtivo(false);
	}

	public void abrir(){
		setAberto(true);
	}

	public void fechar(){
		setAberto(false);
	}

	public boolean removerFormaPagamento(FormaPagamento formaPagamento){
		return getFormasPagamento().remove(formaPagamento);
	}

	public boolean associarFormaPagamento(FormaPagamento formaPagamento){
		return getFormasPagamento().add(formaPagamento);
	}

	public boolean removerProduto(Produto produto){
		return getProdutos().remove(produto);
	}

	public boolean adicionarProduto(Produto produto){
		return getProdutos().add(produto);
	}
	
}
