package com.barrostech.api.model.converter;

import com.barrostech.api.input.UsuarioDTOInput;
import com.barrostech.api.input.UsuarioDTOInputUpdate;
import com.barrostech.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDTOtoUsuarioDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario dtoToDomain(UsuarioDTOInput dtoInput){
        return modelMapper.map(dtoInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioDTOInputUpdate dtoInput, Usuario usuario){
        modelMapper.map(dtoInput, usuario);
    }

    public void copyToDomainObjectPassword(UsuarioDTOPasswordInput dtoInput, Usuario usuario){
        modelMapper.map(dtoInput, usuario);
    }
}
