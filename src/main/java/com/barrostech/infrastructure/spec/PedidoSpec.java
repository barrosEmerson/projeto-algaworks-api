package com.barrostech.infrastructure.spec;

import com.barrostech.domain.model.Pedido;
import com.barrostech.domain.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;


public class PedidoSpec {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro){
        return (root, criteriaQuery, criteriaBuilder) -> {
            if(Pedido.class.equals(criteriaQuery.getResultType())){
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");

            }
            var predicates = new ArrayList<Predicate>();

            if(filtro.getClienteId() != null){
                predicates.add(criteriaBuilder.equal(root.get("cliente"),filtro.getClienteId()));
            }
            if(filtro.getRestauranteId() != null){
                predicates.add(criteriaBuilder.equal(root.get("restaurante"),filtro.getRestauranteId()));
            }
            if(filtro.getDataCriacaoInicio() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"),filtro.getDataCriacaoInicio()));
            }
            if(filtro.getDataCriacaoFim() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"),filtro.getDataCriacaoFim()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
