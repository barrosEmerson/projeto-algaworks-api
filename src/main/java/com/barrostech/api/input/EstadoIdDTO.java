package com.barrostech.api.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstadoIdDTO {

    @NotNull
    private Long id;
}
