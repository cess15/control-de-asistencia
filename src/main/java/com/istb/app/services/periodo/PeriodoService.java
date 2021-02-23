package com.istb.app.services.periodo;

import org.springframework.data.domain.Sort;

import com.istb.app.entities.Periodo;
import com.istb.app.models.DataTableResponse;

public interface PeriodoService {
	
	DataTableResponse findAll(Integer draw, Integer start, Integer length, String search, Sort.Direction sort, String... properties) throws Exception;
	
	void save(Periodo periodo, Periodo periodoVigente);

	void update(Periodo periodo);

	Periodo findPeriodoVigente();
	
	Periodo findById(int id);
	
	void deleteById(int id);
}
