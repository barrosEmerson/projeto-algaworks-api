package com.barrostech.api.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CozinhaDTOInput {

    @NotBlank
    private String nome;
}
