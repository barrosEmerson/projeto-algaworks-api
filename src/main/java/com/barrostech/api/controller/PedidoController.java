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
import com.barrostech.domain.services.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public List<PedidoResumoDTO> listar(){
        List<Pedido> todosPedidos = pedidoRepository.findAll();

        return pedidoResumoDTOConverter.getListPedidoDTO(todosPedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO buscar(@PathVariable Long pedidoId){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);

        return dtoConverter.getPedidoDTO(pedido);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionar(@Valid @RequestBody PedidoDTOInput pedidoInput) {
        try {
            Pedido novoPedido = dtOtoDomain.dtoToDomain(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedidoService.emitir(novoPedido);

            return dtoConverter.getPedidoDTO(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

}
