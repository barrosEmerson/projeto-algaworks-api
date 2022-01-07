package com.barrostech.domain.exception;



public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public CozinhaNaoEncontradaException(String mensagem){
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(Long cidadeId){
        this(String.format("Não existe em cadastro de cozinha com o código %d", cidadeId));
    }
}
