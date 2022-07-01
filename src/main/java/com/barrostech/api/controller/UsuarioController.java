package com.barrostech.api.controller;

import com.barrostech.api.dto.UsuarioDTO;
import com.barrostech.api.input.UsuarioDTOInput;
import com.barrostech.api.input.UsuarioDTOInputUpdate;
import com.barrostech.api.model.converter.UsuarioDTOConverter;
import com.barrostech.api.model.converter.UsuarioDTOPasswordInput;
import com.barrostech.api.model.converter.UsuarioDTOtoUsuarioDomain;
import com.barrostech.domain.exception.SenhasInconstantesException;
import com.barrostech.domain.model.Usuario;
import com.barrostech.domain.repository.UsuarioRepository;
import com.barrostech.domain.services.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private UsuarioDTOConverter dtoConverter;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioDTOtoUsuarioDomain domain;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return dtoConverter.getListUsuarioDTO(usuarioRepository.findAll());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
        return dtoConverter.getUsuarioDTO(usuarioService.buscarOuFalhar(usuarioId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO salvar(@RequestBody @Valid UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario = domain.dtoToDomain(usuarioDTOInput);
        return dtoConverter.getUsuarioDTO(usuarioService.salvar(usuario));
    }

    @PutMapping("/{usuarioId}")
    public UsuarioDTO atualizar(@PathVariable Long usuarioId, @RequestBody UsuarioDTOInputUpdate dtoInput) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
        domain.copyToDomainObject(dtoInput, usuarioAtual);
        return dtoConverter.getUsuarioDTO(usuarioService.salvar(usuarioAtual));
    }

    @PutMapping("/{usuarioId}/senha")
    public UsuarioDTO atualizarSenha(@PathVariable Long usuarioId, @RequestBody UsuarioDTOPasswordInput dtoInput) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);


            if (usuarioAtual.getSenha().equals(dtoInput.getSenhaAtual())) {
                usuarioAtual.setSenha(dtoInput.getNovaSenha());
                return dtoConverter.getUsuarioDTO(usuarioService.salvar(usuarioAtual));
            }else{

                throw new SenhasInconstantesException("Senha atual está incorreta");
            }


    }
}
