package com.barrostech.api.model.converter;

import com.barrostech.api.dto.GrupoDTO;
import com.barrostech.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoDTO getGrupoDTO(Grupo grupo){
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    public List<GrupoDTO> getListGrupoDTO(List<Grupo>grupos){
        return grupos.stream().map(grupo -> getGrupoDTO(grupo)).collect(Collectors.toList());
    }

}
