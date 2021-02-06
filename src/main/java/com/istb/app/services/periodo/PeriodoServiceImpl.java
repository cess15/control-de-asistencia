package com.istb.app.services.periodo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.istb.app.entities.Periodo;
import com.istb.app.repository.PeriodoRepository;

@Service
public class PeriodoServiceImpl implements PeriodoService {

	@Autowired
	PeriodoRepository periodoRepository;

	@Override
	public void save(Periodo periodo) {
		periodoRepository.save(periodo);

	}

	@Override
	public void update(Periodo periodo) {
		periodoRepository.save(periodo);

	}
}
