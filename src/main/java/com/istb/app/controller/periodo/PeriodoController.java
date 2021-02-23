package com.istb.app.controller.periodo;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.istb.app.entities.Periodo;
import com.istb.app.services.auth.UserCredentials;
import com.istb.app.services.periodo.PeriodoService;

@Controller
@RequestMapping("/periodos")
public class PeriodoController {
	
	@Autowired
	UserCredentials userCredentials;
	
	@Autowired
	PeriodoService servicePeriodo;
	
	@GetMapping(value = {"/", ""})
	public String loadView(Model model) {
		model.addAttribute("title","Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());
		return "dashboard/periodos/periodos";
	}
	
	@GetMapping(value = "/create")
	public String loadViewCreate(Periodo periodo, Model model) {
		model.addAttribute("title","Panel ISTB - Crear periodo");
		model.addAttribute("user", userCredentials.getUserAuth());
		model.addAttribute("periodo", periodo);
		
		return "dashboard/periodos/create";
	}
	
	@PostMapping(value = "/save")
	public String save (@Valid @ModelAttribute Periodo periodo, BindingResult result,
			RedirectAttributes redirectAttrs, Model model) {
		
		if (result.hasErrors()) {
			model.addAttribute("title","Panel ISTB - Crear periodo");
			model.addAttribute("user", userCredentials.getUserAuth());
			model.addAttribute("periodo", periodo);
			return "dashboard/periodos/create";
		}
		
		Periodo periodoVigente = this.servicePeriodo.findPeriodoVigente();
		
		if (periodo.getFechaInicio().isBefore(LocalDate.now()) 
				|| periodo.getFechaInicio().isEqual(LocalDate.now())) {
			
			redirectAttrs.addFlashAttribute("error", "La fecha de inicio del periodo debe ser mayor a la actual");
			return "redirect:/periodos/create";
		} 
		
		if (periodo.getFechaFinal().isBefore(LocalDate.now())
				|| periodo.getFechaFinal().isEqual(LocalDate.now())) {
			
			redirectAttrs.addFlashAttribute("error", "La fecha final del periodo debe ser mayor a la actual.");
			return "redirect:/periodos/create";
		} 
		
		if (periodoVigente != null) {
			if (periodoVigente.getFechaFinal().isAfter(LocalDate.now())) {
				redirectAttrs.addFlashAttribute("error", "Aún no se ha terminado el periodo anterior.");
				return "redirect:/periodos/create";
			} 
		}
	
		servicePeriodo.save(periodo, periodoVigente);
		
		redirectAttrs.addFlashAttribute("success", "Se ha agregado un nuevo periodo.");
		return "redirect:/periodos";
	}
	
	@GetMapping(value = "/{id}/editar")
	public String loadViewEdit(@PathVariable int id, Model model) {
		Periodo periodo = servicePeriodo.findById(id);
		model.addAttribute("title","Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());
		model.addAttribute("periodo", periodo);
		
		return "dashboard/periodos/edit";
	}
	
	@PostMapping(value = "/{id}/update")
	public String update (@PathVariable int id, @Valid @ModelAttribute Periodo periodo, BindingResult result,
			RedirectAttributes redirectAttrs, Model model) {
		
		if (result.hasErrors()) {
			model.addAttribute("title","Panel ISTB - Editar periodo");
			model.addAttribute("user", userCredentials.getUserAuth());
			model.addAttribute("periodo", periodo);
			return "dashboard/periodos/edit";
		}
		
		servicePeriodo.update(periodo);
		
		redirectAttrs.addFlashAttribute("success", "Periodo actualizado.");
		return "redirect:/periodos";
	}
	
	@PostMapping(value = "/{id}/borrar")
	public String destroy(@PathVariable int id, RedirectAttributes redirectAttrs) {
		this.servicePeriodo.deleteById(id);

		redirectAttrs.addFlashAttribute("success", "Se ha eliminado un periodo.");
		return "redirect:/periodos";
	}
}
