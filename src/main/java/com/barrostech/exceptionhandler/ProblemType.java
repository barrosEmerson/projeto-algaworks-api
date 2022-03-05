package com.barrostech.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    JSON_INVALIDO("/json-incorreto","Formato de Json errado"),
    PARAMETRO_INVALIDO("/parametro-incorreto","Parametro informado incorreto"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado","Recurso não encontrado"),
    ERRO_NO_SISTEMA("/erro-interno-sistema","Erro interno do sistema"),
    ENTIDADE_EM_USO("/entidade-em-uso","Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio","Violação na regra de negócio");


    private String uri;
    private String title;


    ProblemType(String path, String title){
        this.uri = "http://link-for-error-page"+path;
        this.title = title;
    }

}