package com.barrostech.infrastructure.service;

import com.barrostech.domain.filter.VendaDiariaFilter;
import com.barrostech.domain.model.dto.VendaDiaria;
import com.barrostech.domain.services.VendaQueryService;

import java.util.List;

public class VendaQueryServiceImpl implements VendaQueryService {
    @Override
    public List<VendaDiaria> consultaVendasDiarias(VendaDiariaFilter filter) {
        return null;
    }
}
