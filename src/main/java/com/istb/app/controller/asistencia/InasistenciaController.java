package com.istb.app.controller.asistencia;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.istb.app.entities.Motivo;
import com.istb.app.entities.Permiso;
import com.istb.app.repository.MotivoRepository;
import com.istb.app.services.auth.UserCredentials;
import com.istb.app.services.inasistencia.InasistenciaService;
import com.istb.app.services.motivo.MotivoService;
import com.istb.app.services.permiso.PermisoService;
import com.istb.app.util.enums.TipoMotivo;

@Controller
public class InasistenciaController {

	@Autowired
	UserCredentials userCredentials;

	@Autowired
	MotivoRepository motiveRepository;

	@Autowired
	MotivoService motiveService;

	@Autowired
	PermisoService permisoService;

	@Autowired
	InasistenciaService inasistenciaService;

	@GetMapping(value = "/justificar-inasistencia/{id}", produces = "text/plain")
	public String index(@PathVariable int id, Model model) {
		Permiso permiso = new Permiso();
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());
		model.addAttribute("tipoMotivoPermisos", motiveRepository.findByTipo(TipoMotivo.PERMISO));
		model.addAttribute("tipoMotivoLicencias", motiveRepository.findByTipo(TipoMotivo.LICENCIA));
		model.addAttribute("tipoMotivoOtros", motiveRepository.findByTipo(TipoMotivo.OTROS));
		model.addAttribute("permiso", permiso);
		model.addAttribute("inasistencia_id", id);
		return "dashboard/inasistencias/justificar-inasistencia";
	}

	@PostMapping(value = "/generar-justificativo/{justificacionId}", produces = "text/plain")
	public String showView(@PathVariable int justificacionId, @ModelAttribute("permiso") Permiso permiso,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {
		redirectAttrs.addFlashAttribute("oldValues", permiso);
		return "redirect:/justificar-inasistencia/" + justificacionId;
	}

	@PostMapping(value = "/generar-justificativo/{justificacionId}/motivo/{motivoId}", produces = "text/plain")
	public String addPermiso(@Valid @PathVariable int justificacionId, @PathVariable int motivoId,
			@ModelAttribute("permiso") Permiso permiso, BindingResult result, Model model,
			RedirectAttributes redirectAttrs) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Panel ISTB");
			model.addAttribute("user", userCredentials.getUserAuth());
			model.addAttribute("tipoMotivoPermisos", motiveRepository.findByTipo(TipoMotivo.PERMISO));
			model.addAttribute("tipoMotivoLicencias", motiveRepository.findByTipo(TipoMotivo.LICENCIA));
			model.addAttribute("tipoMotivoOtros", motiveRepository.findByTipo(TipoMotivo.OTROS));
			return "dashboard/inasistencias/justificar-inasistencia";
		}

		Map<String, String> errors = permisoService.save(justificacionId, motivoId, permiso);

		if (errors.isEmpty()) {

			errors.put("clase", "success");
			errors.put("mensaje", "Justificado correctamente");
			redirectAttrs.addFlashAttribute("errors", errors);

		} else {
			errors.put("clase", "danger");
			errors.put("mensaje", "Ocurrio un error al justificar la inasistencia!");
			redirectAttrs.addFlashAttribute("errors", errors);

		}

		redirectAttrs.addFlashAttribute("oldValues", permiso);
		return "redirect:/inicio";

	}

	@GetMapping(value = "generar-inasistencia/{inasistenciaId}", produces = "text/plain")
	public String getInasistenciaJustified(@PathVariable int inasistenciaId, Model model) {
		
		Permiso permiso = permisoService.findbyInasistenciaIdAndProfesorId(inasistenciaId);
		
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());
		model.addAttribute("permiso", permiso);
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

		System.out.println("PDF Generated!");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);

		return new ResponseEntity<>(permisoService.generatePDF(permisoId).toByteArray(), headers, HttpStatus.OK);

	}
}
