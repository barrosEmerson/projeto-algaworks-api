package com.barrostech.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(Long restauranteId, Long produtoId){
        this(String.format("Não existe em cadastro de produto com o código %d para o restaurante %d ",restauranteId, produtoId));
    }
}
