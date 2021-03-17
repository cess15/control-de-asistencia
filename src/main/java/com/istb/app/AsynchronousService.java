package com.istb.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.istb.app.entities.Inasistencia;
import com.istb.app.repository.InasistenciaRepository;
import com.istb.app.services.inasistencia.InasistenciaService;

@Component
public class AsynchronousService {

	@Autowired
	InasistenciaRepository inasistenciaRepository;

	@Autowired
	InasistenciaService inasistenciaService;

	@Scheduled(cron = "0 0 8 * * MON-FRI", zone = "America/Guayaquil")
	public void sendMails() {
		List<Inasistencia> inasistenciasInjustified = inasistenciaRepository
				.findByJustificacionDigitalAndJustificacionFisicaAndPeriodo_VigenteIsTrue(false, false);

		if (inasistenciasInjustified.size() > 0) {
			inasistenciaService.sendEmailByInjustified(inasistenciasInjustified);
		}

		List<Inasistencia> inasistenciasJustified = inasistenciaRepository
				.findByJustificacionDigitalAndJustificacionFisicaAndPeriodo_VigenteIsTrue(true, false);

		if (inasistenciasJustified.size() > 0) {
			inasistenciaService.sendEmailByJustified(inasistenciasJustified);
		}

	}

}
