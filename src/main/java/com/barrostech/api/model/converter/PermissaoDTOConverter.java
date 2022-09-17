package com.barrostech.api.model.converter;

import com.barrostech.api.dto.PermissaoDTO;
import com.barrostech.api.dto.ProdutoDTO;
import com.barrostech.domain.model.Permissao;
import com.barrostech.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoDTO getPermissaoDTO(Permissao permissao){
        return modelMapper.map(permissao, PermissaoDTO.class);
    }

    public List<PermissaoDTO> getListPermissaoDTO(Collection<Permissao> permissoes){
        return permissoes.stream().map(permissao -> getPermissaoDTO(permissao)).collect(Collectors.toList());
    }
}
