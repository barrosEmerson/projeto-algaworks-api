package com.barrostech.api.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
public class PermissaoDTOInput {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
}
