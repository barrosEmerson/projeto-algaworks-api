package com.barrostech.core.jackson;

import com.barrostech.api.model.mixin.CidadeMixin;
import com.barrostech.api.model.mixin.CozinhaMixin;
import com.barrostech.api.model.mixin.RestauranteMixin;
import com.barrostech.api.model.mixin.UsuarioMixin;
import com.barrostech.domain.model.Cidade;
import com.barrostech.domain.model.Cozinha;
import com.barrostech.domain.model.Restaurante;
import com.barrostech.domain.model.Usuario;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule(){
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Usuario.class, UsuarioMixin.class);
    }
}
