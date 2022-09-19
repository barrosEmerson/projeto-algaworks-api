package com.barrostech.api.controller;

import com.barrostech.api.model.converter.RestauranteDTOConverter;
import com.barrostech.api.model.converter.RestauranteDTOtoRestautanteDomain;
import com.barrostech.api.dto.RestauranteDTO;
import com.barrostech.api.input.RestauranteDTOInput;
import com.barrostech.domain.exception.CidadeNaoEncontradaException;
import com.barrostech.domain.exception.CozinhaNaoEncontradaException;
import com.barrostech.domain.exception.NegocioException;
import com.barrostech.domain.exception.RestauranteNaoEncontradoException;
import com.barrostech.domain.model.Restaurante;
import com.barrostech.domain.repository.RestauranteRepository;
import com.barrostech.domain.services.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestauranteDTOConverter converter;

    @Autowired
    private RestauranteDTOtoRestautanteDomain restautanteDomain;

    @GetMapping
    public List<RestauranteDTO> lista(){
        return converter.getListRestauranteDTO(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteDTO buscar(@PathVariable Long restauranteId){

        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        RestauranteDTO restauranteDto = converter.getRestauranteDTO(restaurante);

        return restauranteDto;

    }



    @PostMapping
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteDTOInput restauranteDTOInput){
        Restaurante restaurante = restautanteDomain.toDomainObeject(restauranteDTOInput);
        try {
            return converter.getRestauranteDTO(cadastroRestauranteService.salvar(restaurante));
        }catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }

    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(@PathVariable  Long restauranteId,
                                       @RequestBody @Valid RestauranteDTOInput restauranteDTOInput) {
            Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

                    restautanteDomain.copyToDomainObject(restauranteDTOInput,restauranteAtual);

                try {
                    return converter.getRestauranteDTO(cadastroRestauranteService.salvar(restauranteAtual));

                }catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e){
                    throw new NegocioException(e.getMessage());
                }
    }



//    @PatchMapping("/{restauranteId}")
//    public RestauranteDTO atualizarParcial(@PathVariable Long restauranteId,
//                                        @RequestBody Map<String, Object> campos, HttpServletRequest request) {
//        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
//
//        merge(campos, restauranteAtual, request);
//        validate(restauranteAtual, "restaurante");
//
//        return atualizar(restauranteId, restauranteAtual);
//    }
//
//    private void validate(Restaurante restaurante, String objectName) {
//        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
//
//        validator.validate(restaurante, bindingResult);
//
//        if(bindingResult.hasErrors()){
//            throw new ValidacaoException(bindingResult);
//        }
//
//
//
//    }

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

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId){
        cadastroRestauranteService.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId){
        cadastroRestauranteService.inativar(restauranteId);
    }
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long>restauranteIds){
        try {
            cadastroRestauranteService.ativar(restauranteIds);
        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(),e);
        }
    }
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long>restauranteIds){
        try {
            cadastroRestauranteService.inativar(restauranteIds);
        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(),e);
        }
    }
    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fecharRestaurante(@PathVariable  Long restauranteId) {
        cadastroRestauranteService.fecharRestaurante(restauranteId);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrirRestaurante(@PathVariable  Long restauranteId) {
        cadastroRestauranteService.abrirRestaurante(restauranteId);
    }

}
