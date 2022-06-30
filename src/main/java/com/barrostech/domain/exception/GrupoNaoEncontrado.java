package com.barrostech.domain.exception;

public class GrupoNaoEncontrado extends EntidadeNaoEncontradaException {
    public GrupoNaoEncontrado(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontrado(Long grupoId){
        this(String.format("Não existe cadastro de grupo com o código %d",grupoId));
    }
}
