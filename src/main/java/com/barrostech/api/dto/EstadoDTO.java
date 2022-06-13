package com.barrostech.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstadoDTO {

    private Long id;
    private String nome;
}
