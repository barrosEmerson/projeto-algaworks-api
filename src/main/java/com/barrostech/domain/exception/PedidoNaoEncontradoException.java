package com.barrostech.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
//    public PedidoNaoEncontradoException(String mensagem) {
//        super(mensagem);
//    }

    public PedidoNaoEncontradoException(String codigo){
        super(String.format("Não existe cadastro de pedido com o código %s",codigo));
    }
}
