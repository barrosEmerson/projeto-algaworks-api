package com.barrostech.api.input;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioDTOInputUpdate {

    @NotBlank
    private String nome;
    @Email
    private String email;
}
