package com.barrostech.api.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;

@Getter
@Setter
public class UsuarioDTOInput {

    @NotBlank
    private String nome;
    @Email
    private String email;
    @NotBlank
    private String senha;

    private OffsetDateTime dataCadastro = OffsetDateTime.now();
}
