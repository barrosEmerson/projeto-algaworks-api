package com.barrostech.api.dto;

import com.barrostech.api.input.EstadoIdDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeDTO {

    private Long id;
    private String nome;
    private EstadoIdDTO estado;
}
