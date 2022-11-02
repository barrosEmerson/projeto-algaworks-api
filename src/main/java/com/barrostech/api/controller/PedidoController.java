package com.barrostech.api.controller;

import com.barrostech.api.dto.PedidoDTO;
import com.barrostech.api.dto.PedidoResumoDTO;
import com.barrostech.api.input.PedidoDTOInput;
import com.barrostech.api.model.converter.PedidoDTOConverter;
import com.barrostech.api.model.converter.PedidoDTOtoDomain;
import com.barrostech.api.model.converter.PedidoResumoDTOConverter;
import com.barrostech.domain.exception.EntidadeNaoEncontradaException;
import com.barrostech.domain.exception.NegocioException;
import com.barrostech.domain.model.Pedido;
import com.barrostech.domain.model.Usuario;
import com.barrostech.domain.repository.PedidoRepository;
import com.barrostech.domain.repository.filter.PedidoFilter;
import com.barrostech.domain.services.EmissaoPedidoService;
import com.barrostech.infrastructure.spec.PedidoSpec;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoDTOConverter dtoConverter;
    @Autowired
    private PedidoResumoDTOConverter pedidoResumoDTOConverter;

    @Autowired
    private PedidoDTOtoDomain dtOtoDomain;


//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos){
//        List<Pedido> todosPedidos = pedidoRepository.findAll();
//        List<PedidoResumoDTO> pedidoResumoDTOS = pedidoResumoDTOConverter.getListPedidoDTO(todosPedidos);
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidoResumoDTOS);
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if(StringUtils.isNotBlank(campos)){
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//
//        pedidosWrapper.setFilters(filterProvider);
//
//        return pedidosWrapper;
//    }
    @GetMapping
    public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable){
        Page<Pedido> todosPedidosPage = pedidoRepository.findAll(PedidoSpec.usandoFiltro(filtro),pageable);
        List<PedidoResumoDTO> pedidoResumoDTOS = pedidoResumoDTOConverter.getListPedidoDTO(todosPedidosPage.getContent());
        Page<PedidoResumoDTO> pedidoResumoDTOPage = new PageImpl<>(pedidoResumoDTOS,pageable, todosPedidosPage.getTotalElements());
        return pedidoResumoDTOPage;
    }

    @GetMapping("/{codigo}")
    public PedidoDTO buscar(@PathVariable String codigo){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);

        return dtoConverter.getPedidoDTO(pedido);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionar(@Valid @RequestBody PedidoDTOInput pedidoInput) {
        try {
            Pedido novoPedido = dtOtoDomain.dtoToDomain(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(3L);

            novoPedido = emissaoPedidoService.emitir(novoPedido);

            return dtoConverter.getPedidoDTO(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

}
