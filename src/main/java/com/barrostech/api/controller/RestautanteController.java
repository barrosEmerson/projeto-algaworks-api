package com.barrostech.api.controller;

import com.barrostech.domain.exception.CozinhaNaoEncontradaException;
import com.barrostech.domain.exception.EntidadeNaoEncontradaException;
import com.barrostech.domain.exception.NegocioException;
import com.barrostech.domain.model.Cozinha;
import com.barrostech.domain.model.Restaurante;
import com.barrostech.domain.repository.RestauranteRepository;
import com.barrostech.domain.services.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestautanteController {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> lista(){
        return restauranteRepository.findAll();
    }

    @GetMapping("/{restauranteId}")
    public Restaurante buscar(@PathVariable Long restauranteId){

        return cadastroRestauranteService.buscarOuFalhar(restauranteId);
    }

    @PostMapping
    public Restaurante adicionar(@RequestBody Restaurante restaurante){
        try {
            return cadastroRestauranteService.salvar(restaurante);
        }catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }

    }

    @PutMapping("/{restauranteId}")
    public Restaurante atualizar(@PathVariable Long restauranteId,
                                       @RequestBody Restaurante restaurante) {
            Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

                BeanUtils.copyProperties(restaurante, restauranteAtual, "id","formasPagamento","endereco","dataCadastro");

                try {
                    return cadastroRestauranteService.salvar(restauranteAtual);

                }catch (CozinhaNaoEncontradaException e){
                    throw new NegocioException(e.getMessage());
                }


    }

    @PatchMapping("/{restauranteId}")
    public Restaurante atualizarParcial(@PathVariable Long restauranteId,
                                        @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        merge(campos, restauranteAtual, request);

        return atualizar(restauranteId, restauranteAtual);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {

        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);


            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

//			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        }catch (IllegalArgumentException e){
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }

    }
}
