package com.barrostech.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada","Entidade não encontrada");


    private String uri;
    private String title;


    ProblemType(String path, String title){
        this.uri = "http://link-for-error-page"+path;
        this.title = title;
    }

}
