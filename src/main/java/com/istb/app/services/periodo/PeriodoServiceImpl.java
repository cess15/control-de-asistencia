package com.istb.app.services.periodo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istb.app.entities.Periodo;
import com.istb.app.models.DataTableResponse;
import com.istb.app.repository.PeriodoRepository;

@Service
public class PeriodoServiceImpl implements PeriodoService {

	@Autowired
	PeriodoRepository periodoRepository;

	@Transactional
	@Override
	public void save(Periodo periodo, Periodo periodoVigente) {
		if (periodoVigente != null) {
			periodoVigente.setVigente(false);
			periodoRepository.save(periodoVigente);
		}
		
		periodo.setVigente(true);
		periodoRepository.save(periodo);
	}

	@Override
	public void update(Periodo periodo) {
		periodoRepository.save(periodo);
	}

	@Override
	public Periodo findPeriodoVigente() {
		return periodoRepository.findByVigente(true);
	}

	@Override
	public DataTableResponse findAll(Integer draw, Integer start, Integer length, String search, Sort.Direction sort, String... properties) throws Exception {
		int page = 0;
		
		if (start > 0 && length >= start) {
			page = length / start;
		} else if (start > 0 && length < start) {
			page = start / length;
		}
		
		long count = 0;
		List<Periodo> periodos = null;
		
		if (!search.isEmpty() && search.length() == 4 && validateNumber(search)) {
			int anio = getDateSearch(search);

			count = periodoRepository.countPeriodo(anio);
			
			periodos = periodoRepository
				.findAll(anio, PageRequest.of(page, length, sort, properties))
				.getContent();
		} else {
			count = periodoRepository.count();
			
			periodos = periodoRepository
				.findAll(PageRequest.of(page, length, sort, properties))
				.getContent();
		}
		
		return new DataTableResponse(draw, count,count, periodos);
	}
	
	private static int getDateSearch (String search) {
		int anio = 0;
		if (search.length() == 4) {
			if (validateNumber(search)) {
				anio = Integer.parseInt(search);
			}		
		}
		
		return anio;
	}
	
	private static boolean validateNumber (String str) {
		boolean valid = false;
		try {
			Integer.parseInt(str);
			valid = true;
		}catch(NumberFormatException e) {}
		
		return valid;
	}

	@Override
	public Periodo findById(int id) {
		return this.periodoRepository
			.findById(id)
			.orElse(null);
	}

	@Transactional
	@Override
	public void deleteById(int id) {
		this.periodoRepository.deleteById(id);
	}
}
