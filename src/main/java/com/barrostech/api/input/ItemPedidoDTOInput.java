package com.barrostech.api.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoDTOInput {

    @NotNull
    private Long produtoId;
    @NotNull
    private BigDecimal quantidade;

    private String observacao;
}
