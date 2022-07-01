package com.barrostech.api.model.converter;

import com.barrostech.api.input.RestauranteDTOInput;
import com.barrostech.domain.model.Cidade;
import com.barrostech.domain.model.Cozinha;
import com.barrostech.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDTOtoRestautanteDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante
    toDomainObeject(RestauranteDTOInput dtoInput){

        return modelMapper.map(dtoInput, Restaurante.class);

    }

    public void copyToDomainObject(RestauranteDTOInput restauranteDTOInput, Restaurante restaurante){
        //Para evitar exception por serialização de cozinha.id
//        org.hibernate.HibernateException: identifier of an instance of com.barrostech.algafood.damain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());

        if(restaurante.getEndereco() !=null){
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteDTOInput,restaurante);
    }
}
