package com.barrostech.api.controller;

import com.barrostech.api.dto.FormaPagamentoDTO;
import com.barrostech.api.dto.ProdutoDTO;
import com.barrostech.api.input.ProdutoDTOInput;
import com.barrostech.api.model.converter.ProdutoDTOConverter;
import com.barrostech.api.model.converter.ProdutoDTOtoDomain;
import com.barrostech.domain.model.Produto;
import com.barrostech.domain.model.Restaurante;
import com.barrostech.domain.repository.ProdutoRepository;
import com.barrostech.domain.services.CadastroProdutoService;
import com.barrostech.domain.services.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produto")
public class RestautanteProdutosController {


    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ProdutoDTOConverter dtoConverter;
    @Autowired
    private ProdutoDTOtoDomain dtOtoDomain;

    @GetMapping
    public List<ProdutoDTO> lista(@PathVariable Long restauranteId){
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        List<Produto> todosProdutos = produtoRepository.findByRestaurante(restaurante);
        return dtoConverter.getListProdutoDTO(todosProdutos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoDTO lista(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
        return dtoConverter.getProdutoDTO(produto);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(@PathVariable Long restauranteId, @RequestBody ProdutoDTOInput produtoDTOInput){

        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        Produto produto = dtOtoDomain.dtoToDomain(produtoDTOInput);
        produto.setRestaurante(restaurante);

        produto = cadastroProdutoService.salvar(produto);
        return dtoConverter.getProdutoDTO(produto);

    }

    @PutMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO atualizar(@PathVariable Long restauranteId,@PathVariable Long produtoId, @RequestBody ProdutoDTOInput produtoDTOInput){

        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        Produto produtoAtual = cadastroProdutoService.buscarOuFalhar(restauranteId,produtoId);
        dtOtoDomain.copyToDomainObject(produtoDTOInput,produtoAtual);

        produtoAtual = cadastroProdutoService.salvar(produtoAtual);
        return dtoConverter.getProdutoDTO(produtoAtual);

    }


}
