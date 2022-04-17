package com.barrostech.api.model.mixin;

import com.barrostech.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes = new ArrayList<>();
}
