package com.barrostech.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long grupoId){
        this(String.format("Não existe cadastro de grupo com o código %d",grupoId));
    }
}
