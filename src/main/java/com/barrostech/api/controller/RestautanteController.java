package com.barrostech.api.controller;

import com.barrostech.api.dto.CozinhaDTO;
import com.barrostech.api.dto.RestauranteDTO;
import com.barrostech.api.input.RestauranteDTOInput;
import com.barrostech.core.validation.ValidacaoException;
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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes")
public class RestautanteController {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private SmartValidator validator;

    @GetMapping
    public List<RestauranteDTO> lista(){
        return getListRestauranteDTO(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteDTO buscar(@PathVariable Long restauranteId){

        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        RestauranteDTO restauranteDto = getRestauranteDTO(restaurante);

        return restauranteDto;

    }



    @PostMapping
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteDTOInput restauranteDTOInput){
        Restaurante restaurante = toDomainObeject(restauranteDTOInput);
        try {
            return getRestauranteDTO(cadastroRestauranteService.salvar(restaurante));
        }catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }

    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(@PathVariable  Long restauranteId,
                                       @RequestBody @Valid RestauranteDTOInput restauranteDTOInput) {
            Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
            Restaurante restaurante = toDomainObeject(restauranteDTOInput);


                BeanUtils.copyProperties(restaurante, restauranteAtual, "id","formasPagamento","endereco","dataCadastro");

                try {
                    return getRestauranteDTO(cadastroRestauranteService.salvar(restauranteAtual));

                }catch (CozinhaNaoEncontradaException e){
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

    private RestauranteDTO getRestauranteDTO(Restaurante restaurante) {
        RestauranteDTO restauranteDto = new RestauranteDTO();
        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        restauranteDto.setId(restaurante.getId());
        restauranteDto.setNome(restaurante.getNome());
        restauranteDto.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDto.setCozinha(cozinhaDTO);
        return restauranteDto;
    }

    private List<RestauranteDTO> getListRestauranteDTO(List<Restaurante> restaurantes){
        return restaurantes.stream().map(restaurante -> getRestauranteDTO(restaurante)).collect(Collectors.toList());
    }

    private Restaurante toDomainObeject(RestauranteDTOInput dtoInput){
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(dtoInput.getNome());
        restaurante.setTaxaFrete(dtoInput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(dtoInput.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        return restaurante;
    }
}
