package com.barrostech.api.controller;

import com.barrostech.domain.filter.VendaDiariaFilter;
import com.barrostech.domain.model.dto.VendaDiaria;
import com.barrostech.domain.services.VendaQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    private VendaQueryService vendaQueryService;

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultaVendasDiarias(VendaDiariaFilter filter){
        return vendaQueryService.consultaVendasDiarias(filter);
    }
}
