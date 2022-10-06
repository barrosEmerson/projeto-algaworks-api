package com.barrostech.api.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoDTOInput {

    @Valid
    @NotNull
    private RestauranteDTOIdInput restaurante;
    @Valid
    @NotNull
    private FormaPagamentoDTOIdInput formaPagamento;

    @Valid
    @NotNull
    private EnderecoDTOInput enderecoEntrega;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoDTOInput> itens = new ArrayList<>();
}
