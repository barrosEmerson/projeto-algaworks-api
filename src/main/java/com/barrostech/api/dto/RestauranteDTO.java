package com.barrostech.api.dto;

import com.barrostech.api.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class RestauranteDTO {

    @JsonView({RestauranteView.Resumo.class,RestauranteView.ApenasNome.class})
    private Long id;
    @JsonView({RestauranteView.Resumo.class,RestauranteView.ApenasNome.class})
    private String nome;
    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;
    @JsonView(RestauranteView.Resumo.class)
    private CozinhaDTO cozinha;
    private Boolean aberto;
    private Boolean ativo;
    private EnderecoDTO endereco;
}
