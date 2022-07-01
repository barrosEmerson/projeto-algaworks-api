package com.barrostech.api.model.converter;

import com.barrostech.api.dto.EstadoDTO;
import com.barrostech.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoDTO getEstadoDTO(Estado estado){
        return modelMapper.map(estado, EstadoDTO.class);
    }

    public List<EstadoDTO> getListEstadoDTO(List<Estado> estados){
        return estados.stream().map(estado -> getEstadoDTO(estado)).collect(Collectors.toList());
    }
}
