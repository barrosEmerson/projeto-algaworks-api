package com.barrostech.domain.exception;

public class SenhasInconstantesException extends NegocioException{
    public SenhasInconstantesException(String mensagem, Throwable causa) {
        super("Senha atual não está correta", causa);
    }
    public SenhasInconstantesException(String message){
        super(message);
    }
}
