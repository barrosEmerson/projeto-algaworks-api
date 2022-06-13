package com.barrostech.api.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeDTOInput {

    @NotBlank
    private String nome;
    @Valid
    @NotNull
    private EstadoIdDTO estado;
}
