package com.istb.app.controller.permiso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.istb.app.entities.Adjunto;
import com.istb.app.entities.Motivo;
import com.istb.app.entities.Permiso;
import com.istb.app.entities.Profesor;
import com.istb.app.repository.MotivoRepository;
import com.istb.app.services.auth.UserCredentials;
import com.istb.app.services.permiso.PermisoService;
import com.istb.app.services.profesor.ProfesorService;
import com.istb.app.util.enums.TipoMotivo;

@Controller
public class PermisoController {

	@Autowired
	PermisoService permisoService;

	@Autowired
	UserCredentials userCredentials;

	@Autowired
	MotivoRepository motiveRepository;

	@Autowired
	ProfesorService profesorService;

	@GetMapping(value = "/licencias-permisos")
	public String showView(Model model) {
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());
		model.addAttribute("permiso", new Permiso());
		return "dashboard/licencias-permisos/licencias-permisos";
	}

	@GetMapping(value = "/ver-permisos/{profesorId}")
	public String showPermisos(@PathVariable int profesorId, Model model) {
		Profesor profesor = profesorService.findById(profesorId);
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());
		model.addAttribute("profesor", profesor);
		return "dashboard/profesores/profesor-permiso";
	}

	@GetMapping(value = "/generar-inasistencia/{inasistenciaId}", produces = "text/plain")
	public String getInasistenciaJustified(@PathVariable int inasistenciaId, Model model) {

		Permiso permiso = permisoService.findbyInasistenciaId(inasistenciaId);
		Adjunto adjunto = new Adjunto();
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());
		model.addAttribute("permiso", permiso);
		model.addAttribute("adjunto", adjunto);
		model.addAttribute("tipoMotivoPermisos", motiveRepository.findByTipo(TipoMotivo.PERMISO));
		model.addAttribute("tipoMotivoLicencias", motiveRepository.findByTipo(TipoMotivo.LICENCIA));
		model.addAttribute("tipoMotivoOtros", motiveRepository.findByTipo(TipoMotivo.OTROS));

		for (Motivo motivo : permiso.getMotivos()) {
			if (motivo.getTipo().toString().equals(TipoMotivo.LICENCIA.toString())) {
				model.addAttribute("LICENCIA", motivo);
				break;
			} else if (motivo.getTipo().toString().equals(TipoMotivo.PERMISO.toString())) {
				model.addAttribute("PERMISO", motivo);
				break;
			} else if (motivo.getTipo().toString().equals(TipoMotivo.OTROS.toString())) {
				model.addAttribute("OTROS", motivo);
				break;
			}
		}

		return "dashboard/inasistencias/generar-inasistencia";

	}

	@GetMapping(value = "/generar-permiso/{permisoId}")
	public ResponseEntity<byte[]> generatePDF(@PathVariable int permisoId) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);

		return new ResponseEntity<>(permisoService.generatePDF(permisoId).toByteArray(), headers, HttpStatus.OK);

	}

	@PostMapping(value = "/permisos/reporte")
	public ResponseEntity<byte[]> generateReport(@ModelAttribute("permiso") Permiso permiso) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		
		String startDate = String.valueOf(permiso.getFechaInicio());
		String finalDate = String.valueOf(permiso.getFechaFinal());

		return new ResponseEntity<>(permisoService.generateReport(startDate, finalDate).toByteArray(), headers,
				HttpStatus.OK);
	}
}
