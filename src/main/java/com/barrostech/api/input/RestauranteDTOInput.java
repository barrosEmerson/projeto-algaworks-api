package com.barrostech.api.input;

import com.barrostech.core.validation.Groups;
import com.barrostech.core.validation.TaxaFrete;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteDTOInput {

    @NotBlank
    private String nome;

    @TaxaFrete
    @PositiveOrZero
    private BigDecimal taxaFrete;

    @Valid
    @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    @NotNull
    private CozinhaIdDTOInput cozinha;
}
