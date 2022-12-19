package org.generation.italy.demo.controller;

import java.util.List;
import java.util.Optional;

import org.generation.italy.demo.pojo.Drink;
import org.generation.italy.demo.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/drink")
public class DrinkController {
	
	@Autowired
	private DrinkService dS;
	
	@GetMapping
	public String index(Model model) {
		
		List<Drink> drinks = dS.findAll();
		model.addAttribute("drinks", drinks);
		return "drink-index";
	}
	
	@GetMapping("/create")
	public String getCreateDrink(Model model) {
		
		Drink drink = new Drink();
		model.addAttribute("drink", drink);
		
		return "drink-create";
	}
	
	@PostMapping("/create")
	public String storeDrink(@Valid @ModelAttribute("drink") Drink drink, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/drink/create";
		}
		
		dS.save(drink);
		return "redirect:/drink";
	}
	
	@GetMapping("/edit/{id}")
	public String getEditDrink(@PathVariable("id") int id, Model model) {
		
		Optional<Drink> optDrink = dS.getDrinkById(id);
		Drink drink = optDrink.get();
		
		model.addAttribute(drink);
		return "drink-edit";
	}
	
	@PostMapping("/edit")
	public String updateDrink(@Valid @ModelAttribute("drink") Drink drink,  BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/drink/edit/" + drink.getId();
		}
		
		dS.save(drink);
		return "redirect:/drink";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteDrink(@PathVariable("id") int id, Model model) {
		
		dS.deleteDrinkById(id);
		return "redirect:/drink";
	}
	
	@GetMapping("/search")
	public String getSearchDrinkByName(Model model, @RequestParam(name = "query", required = false) String query) {
		
		List<Drink> drinks = query == null ? dS.findAll() : dS.findByName(query);
		model.addAttribute("drinks", drinks);
		model.addAttribute("query", query);
		return "drink-search";
 	}
}
