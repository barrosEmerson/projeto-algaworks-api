package com.barrostech.domain.services;

import com.barrostech.domain.exception.EntidadeEmUsoException;
import com.barrostech.domain.exception.EstadoNaoEncontradoException;
import com.barrostech.domain.exception.UsuarioNaoEncontradoException;
import com.barrostech.domain.model.Usuario;
import com.barrostech.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroUsuarioService {

    private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser exclíudo por está sendo utilizado";
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario){
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
}
