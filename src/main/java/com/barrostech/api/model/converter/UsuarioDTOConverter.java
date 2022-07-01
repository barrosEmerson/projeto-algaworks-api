package com.barrostech.api.model.converter;

import com.barrostech.api.dto.UsuarioDTO;
import com.barrostech.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO getUsuarioDTO(Usuario usuario){
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> getListUsuarioDTO(List<Usuario>usuarios){
        return usuarios.stream().map(usuario -> getUsuarioDTO(usuario)).collect(Collectors.toList());
    }
}
