package com.istb.app.services.permiso;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.istb.app.entities.Inasistencia;
import com.istb.app.entities.Motivo;
import com.istb.app.entities.Permiso;
import com.istb.app.entities.Profesor;
import com.istb.app.repository.PermisoRepository;
import com.istb.app.services.auth.UserCredentials;
import com.istb.app.services.inasistencia.InasistenciaService;
import com.istb.app.services.motivo.MotivoService;
import com.istb.app.services.profesor.ProfesorService;

@Service
public class PermisoServiceImpl implements PermisoService {

	@Autowired
	PermisoRepository permisoRepository;

	@Autowired
	ProfesorService profesorService;

	@Autowired
	InasistenciaService inasistenciaService;

	@Autowired
	MotivoService motiveService;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	UserCredentials userCredentials;

	@Value("${app.url}")
	String appUrl;

	@Override
	public Map<String, String> save(int justificacionId, int motivoId, Permiso permiso) {
		Inasistencia inasistencia = inasistenciaService.findById(justificacionId);
		Motivo motivo = motiveService.findById(motivoId);
		List<Inasistencia> inasistenciasJustified = new ArrayList<>();

		Map<String, String> errorAttributes = new HashMap<>();

		if (permiso.getFechaInicio() == null || permiso.getFechaFinal() == null) {
			errorAttributes.put("fechaInicio",
					"El campo " + permiso.getFechaInicio() + " o " + permiso.getFechaFinal() + " son requeridos");
			errorAttributes.put("clase", "text-danger");
			return errorAttributes;
		}

		int dias = permiso.getFechaFinal().getDayOfMonth() - permiso.getFechaInicio().getDayOfMonth();
		permiso.setFechaCreacion(LocalDateTime.now());
		permiso.setFechaActualizacion(LocalDateTime.now());
		permiso.setFechaGeneracion(LocalDate.now());
		permiso.setValorDescontar(permiso.addValorDescontar(dias));
		permiso.setInasistencia(inasistencia);
		permiso.addMotivo(motivo);

		permisoRepository.save(permiso);

		inasistencia.setJustificacionDigital(true);
		inasistenciaService.update(inasistencia);

		if (inasistencia.getJustificacionDigital() == true && inasistencia.getJustificacionFisica() == false) {
			inasistenciasJustified.add(inasistencia);
		}

		if (inasistenciasJustified.size() > 0) {
			inasistenciaService.sendEmailByJustified(inasistenciasJustified);
		}
		return errorAttributes;

	}

	@Override
	public void update(Permiso permiso) {
		permisoRepository.save(permiso);

	}

	@Override
	public ByteArrayOutputStream generatePDF(int permisoId) {

		Permiso permiso = findById(permisoId);
		Context ctx = new Context();

		Map<String, Object> attributes = new HashMap<>();
		attributes.put("permiso", permiso);
		attributes.put("fechaCreacion", convertDateTime(permiso.getFechaCreacion()));

		ctx.setVariable("attributes", attributes);
		String htmlTemplate = templateEngine.process("reporte", ctx);

		ByteArrayOutputStream out = null;

		try {

			out = new ByteArrayOutputStream();
			ITextRenderer render = new ITextRenderer();
			render.setDocumentFromString(htmlTemplate, appUrl);
			render.layout();
			render.createPDF(out, false);
			render.finishPDF();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return out;

	}

	@Override
	public String convertDateTime(LocalDateTime fecha) {
		Locale spanishLocale = new Locale("es", "ES");

		int day = fecha.getDayOfMonth();

		String dayOfWeek = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, spanishLocale);

		String month = fecha.getMonth().getDisplayName(TextStyle.FULL, spanishLocale);

		int year = fecha.getYear();

		return dayOfWeek.concat(", " + day).concat(" de ").concat(month).concat(" de " + year);
	}

	@Override
	public Permiso findById(int permisoId) {
		Optional<Permiso> permiso = permisoRepository.findById(permisoId);
		if (permiso != null) {
			return permiso.get();
		}
		return null;
	}

	@Override
	public Permiso findbyInasistenciaId(int inasistenciaId) {
		return this.permisoRepository.findByInasistencia_IdAndInasistencia_Periodo_VigenteIsTrue(inasistenciaId);
	}

}
