package com.barrostech.domain.services;

import com.barrostech.domain.exception.*;
import com.barrostech.domain.model.Usuario;
import com.barrostech.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
public class CadastroUsuarioService {

    private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser exclíudo por está sendo utilizado";
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntityManager manager;

    @Transactional
    public Usuario salvar(Usuario usuario){
        manager.detach(usuario);

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)){
            throw new NegocioException(String.format("Já existe usuário cadastrado com o e-mail %s",usuario.getEmail()));
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario buscarOuFalhar(Long usuarioId){
        return usuarioRepository.findById(usuarioId).orElseThrow(
                ()-> new UsuarioNaoEncontradoException(usuarioId)
        );
    }

    public void excluir(Long usuarioId){
        try {
            usuarioRepository.deleteById(usuarioId);
        }catch (EmptyResultDataAccessException e){
            throw new EstadoNaoEncontradoException(usuarioId);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_USUARIO_EM_USO,usuarioId)
            );
        }
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha){
        Usuario usuario = buscarOuFalhar(usuarioId);

        if(usuario.senhaConferem(senhaAtual)){
            usuario.setSenha(novaSenha);
        }else {
            throw new SenhasInconstantesException("Senha atual está incorreta");
        }
    }
}
