package com.barrostech.api.converter;

import com.barrostech.api.dto.CidadeDTO;
import com.barrostech.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeDTO getCidadeDTO(Cidade cidade){
        return modelMapper.map(cidade, CidadeDTO.class);
    }

    public List<CidadeDTO> getListCidadeDTO(List<Cidade> cidades){
        return cidades.stream().map(cidade -> getCidadeDTO(cidade)).collect(Collectors.toList());
    }
}
