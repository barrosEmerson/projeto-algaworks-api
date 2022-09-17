package com.barrostech.api.model.converter;

import com.barrostech.api.dto.FormaPagamentoDTO;
import com.barrostech.api.dto.ProdutoDTO;
import com.barrostech.domain.model.FormaPagamento;
import com.barrostech.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO getProdutoDTO(Produto produto){
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> getListProdutoDTO(Collection<Produto> produtos){
        return produtos.stream().map(produto -> getProdutoDTO(produto)).collect(Collectors.toList());
    }
}
