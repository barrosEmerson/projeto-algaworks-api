package com.barrostech.api.model.converter;

import com.barrostech.api.dto.FormaPagamentoDTO;
import com.barrostech.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTO getFormaPagamentoDTO(FormaPagamento formaPagamento){
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    public List<FormaPagamentoDTO> getListFormaPagamentoDTO(Collection<FormaPagamento> formaPagamentos){
        return formaPagamentos.stream().map(formaPagamento -> getFormaPagamentoDTO(formaPagamento)).collect(Collectors.toList());
    }
}
