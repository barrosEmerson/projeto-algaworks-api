package com.barrostech.domain.repository;

import java.util.List;

import com.barrostech.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao,Long> {

}
