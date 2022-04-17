package com.barrostech.api.model.mixin;

import com.barrostech.domain.model.Grupo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class UsuarioMixin {

    @JsonIgnore
    private List<Grupo> grupos = new ArrayList<>();
}
