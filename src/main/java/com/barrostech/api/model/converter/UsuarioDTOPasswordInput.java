package com.barrostech.api.model.converter;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioDTOPasswordInput {

    @NotBlank
    private String senhaAtual;
    @NotBlank
    private String novaSenha;
}
