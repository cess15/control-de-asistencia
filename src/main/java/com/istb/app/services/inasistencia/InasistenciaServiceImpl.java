package com.istb.app.services.inasistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istb.app.entities.Inasistencia;
import com.istb.app.entities.Periodo;
import com.istb.app.entities.Profesor;
import com.istb.app.models.DataTableResponse;
import com.istb.app.repository.InasistenciaRepository;
import com.istb.app.repository.ProfesorRepository;
import com.istb.app.services.auth.UserCredentials;
import com.istb.app.services.mail.MailServiceI;
import com.istb.app.services.periodo.PeriodoService;
import com.istb.app.services.profesor.ProfesorService;

@Service
public class InasistenciaServiceImpl implements InasistenciaService {

	@Autowired
	InasistenciaRepository inasistenciaRepository;

	@Autowired
	PeriodoService servicePeriodo;

	@Autowired
	UserCredentials userCredentials;

	@Autowired
	MailServiceI mailService;

	@Autowired
	ProfesorService profesorService;

	@Autowired
	ProfesorRepository profesorRepository;

	@Value("${app.url}")
	String appUrl;

	@Transactional
	@Override
	public List<Inasistencia> save(Set<Inasistencia> inasistencias, Periodo periodo) {
		List<Inasistencia> inasistenciasPeriodos = new ArrayList<Inasistencia>();
		for (Inasistencia inasistencia : inasistencias) {
			inasistencia.setFecha(LocalDate.now());
			inasistencia.setPeriodo(periodo);
			inasistenciasPeriodos.add(inasistencia);

		}
		List<Inasistencia> inasistenciasGuarded = inasistenciaRepository.saveAll(inasistenciasPeriodos);
		sendEmailByInjustified(inasistenciasGuarded);
		return inasistenciasGuarded;
	}

	@Override
	public void sendEmailByInjustified(List<Inasistencia> inasistencias) {
		Map<String, Object> data = new HashMap<>();
		List<Profesor> profesoresInjustified = new ArrayList<>();
		Set<Integer> idInjustified = new HashSet<>();

		for (Inasistencia inasistencia : inasistencias) {

			if (inasistencia.getJustificacionDigital() == false && inasistencia.getJustificacionFisica() == false) {
				idInjustified.add(inasistencia.getProfesor().getId());
			}

		}

		if (idInjustified.size() > 0) {
			profesoresInjustified = profesorRepository.findAllById(idInjustified);

			for (Profesor profesor : profesoresInjustified) {
				data.put("nombres", profesor.getNombres());
				data.put("apellidos", profesor.getApellidos());
				data.put("inasistencias", profesor.getInasistencias());
				data.put("year", mailService.getFullYear());
				data.put("url", appUrl);
				mailService.sendEmailTemplate("injustified", data, profesor.getCorreo(), "Inasistencia no Justificada");
			}
		}

	}

	@Override
	public void sendEmailByJustified(List<Inasistencia> inasistencias) {
		Map<String, Object> data = new HashMap<>();
		List<Profesor> profesoresJustified = new ArrayList<>();
		Set<Integer> idJustified = new HashSet<>();

		for (Inasistencia inasistencia : inasistencias) {
			if (inasistencia.getJustificacionDigital() == true && inasistencia.getJustificacionFisica() == false) {
				idJustified.add(inasistencia.getProfesor().getId());
			}
		}

		if (idJustified.size() > 0) {
			profesoresJustified = profesorRepository.findAllById(idJustified);
			for (Profesor profesor : profesoresJustified) {
				data.put("nombres", profesor.getNombres());
				data.put("apellidos", profesor.getApellidos());
				data.put("url", appUrl);
				data.put("year", mailService.getFullYear());
				mailService.sendEmailTemplate("justified", data, profesor.getCorreo(),
						"Justificación no entregada Fisicamente");
			}
		}
	}

	@Transactional
	@Override
	public void update(Inasistencia inasistencia) {
		inasistenciaRepository.save(inasistencia);
	}

	@Override
	public List<Inasistencia> findByFechaActual(int profesor_id) {
		return inasistenciaRepository.findByProfesor_IdAndFecha(profesor_id, LocalDate.now());
	}

	@Override
	public Collection<Inasistencia> findByFechaActual() {
		return inasistenciaRepository.findByFecha(LocalDate.now());
	}

	@Override
	public List<Inasistencia> findByInjustificado() {
		Profesor profesor = userCredentials.getUserAuth().getProfesor();
		return this.inasistenciaRepository
				.findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrue(false, false,
						profesor.getId());
	}

	@Override
	public List<Inasistencia> findByJustificado() {
		Profesor profesor = userCredentials.getUserAuth().getProfesor();
		return this.inasistenciaRepository
				.findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrue(true, false,
						profesor.getId());
	}

	@Override
	public Inasistencia findByUltimoInjustificado() {
		Profesor profesor = userCredentials.getUserAuth().getProfesor();
		Periodo periodo = servicePeriodo.findPeriodoVigente();
		return this.inasistenciaRepository
				.findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrueLast(false,
						false, profesor.getId(), periodo.getId(), LocalDate.now());
	}

	@Override
	public Inasistencia findByUltimoJustificado() {
		Profesor profesor = userCredentials.getUserAuth().getProfesor();
		Periodo periodo = servicePeriodo.findPeriodoVigente();
		return this.inasistenciaRepository
				.findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrueLast(true, false,
						profesor.getId(), periodo.getId(), LocalDate.now());
	}

	@Override
	public Inasistencia findByProfesor(int profesor_id) {
		Periodo periodo = servicePeriodo.findPeriodoVigente();
		return this.inasistenciaRepository
				.findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrueLast(false,
						false, profesor_id, periodo.getId(), LocalDate.now());
	}

	@Override
	public Inasistencia findByProfesorJutified(int profesor_id) {
		Periodo periodo = servicePeriodo.findPeriodoVigente();
		return this.inasistenciaRepository
				.findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrueLast(true, false,
						profesor_id, periodo.getId(), LocalDate.now());
	}

	@Override
	public Inasistencia findById(int id) {

		Optional<Inasistencia> inasistencia = inasistenciaRepository.findById(id);
		if (inasistencia.isPresent()) {
			return inasistencia.get();
		}
		return null;
	}

	@Override
	public DataTableResponse findAll(Integer draw, Integer start, Integer length, String search, Direction sort,
			String... properties) throws Exception {
		int page = 0;

		if (start > 0 && length >= start) {
			page = length / start;
		} else if (start > 0 && length < start) {
			page = start / length;
		}

		long count = 0;

		List<Inasistencia> inasistencias = null;
		if (!search.isEmpty() && search.length() == 10) {
			LocalDate date = getSearch(search);
			count = inasistenciaRepository.countInasistencia(date);

			inasistencias = inasistenciaRepository.findAll(date, PageRequest.of(page, length, sort, properties))
					.getContent();
		} else {
			count = inasistenciaRepository.count();

			inasistencias = inasistenciaRepository.findAll(PageRequest.of(page, length, sort, properties)).getContent();
		}

		return new DataTableResponse(draw, count, count, inasistencias);
	}

	private static LocalDate getSearch(String search) {
		if (search.length() == 10) {
			return LocalDate.parse(search);
		}
		return null;

	}

}
