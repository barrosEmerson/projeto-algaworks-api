package com.barrostech.api.model.converter;

import com.barrostech.api.dto.CozinhaDTO;
import com.barrostech.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTO getCozinhaDTO(Cozinha cozinha){
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }

    public List<CozinhaDTO> getListCozinhaDTO(List<Cozinha>cozinhas){
        return cozinhas.stream().map(cozinha -> getCozinhaDTO(cozinha)).collect(Collectors.toList());
    }

}
