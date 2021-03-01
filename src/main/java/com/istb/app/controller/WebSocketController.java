package com.istb.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.istb.app.entities.Inasistencia;
import com.istb.app.services.inasistencia.InasistenciaService;

@Controller
public class WebSocketController {
	
	@Autowired
	InasistenciaService serviceInasistencia;
	
	@MessageMapping("/send")
	@SendTo("/broker/absence")
	public List<Inasistencia> listenSend (Set<Inasistencia> inasistencias) throws Exception {
		List<Inasistencia> _inasistencias = new ArrayList<Inasistencia>();
		for (Inasistencia inasistencia : inasistencias) {
			Inasistencia _inasistencia = this.serviceInasistencia.findByProfesor(inasistencia.getProfesor().getId());
			inasistencia.setFecha(_inasistencia.getFecha());
			inasistencia.setId(_inasistencia.getId());
			_inasistencias.add(inasistencia);
		}
		
		Thread.sleep(1000);
		return _inasistencias;
	}
}
