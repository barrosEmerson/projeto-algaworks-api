package com.barrostech.domain.services;

import com.barrostech.domain.filter.VendaDiariaFilter;
import com.barrostech.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultaVendasDiarias(VendaDiariaFilter filter);
}
