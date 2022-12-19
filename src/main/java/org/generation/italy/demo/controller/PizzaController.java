package org.generation.italy.demo.controller;

import java.util.List;
import java.util.Optional;

import org.generation.italy.demo.pojo.Ingredient;
import org.generation.italy.demo.pojo.Pizza;
import org.generation.italy.demo.pojo.Promotion;
import org.generation.italy.demo.service.IngredientService;
import org.generation.italy.demo.service.PizzaService;
import org.generation.italy.demo.service.PromotionService;
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
@RequestMapping("/pizza")
public class PizzaController {
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private PromotionService prService;
	
	@Autowired
	private IngredientService iService;
	
	@GetMapping("/user")
	public String index(Model model) {
		
		List<Pizza> pizzas = pizzaService.findAll();
		model.addAttribute("pizzas", pizzas);
		return "index";
	}
	
	@GetMapping("/admin/create")
	public String createPizza(Model model) {
		List<Ingredient> ingredients = iService.findAll();
		List<Promotion> promotions = prService.findAll(); 
		Pizza pizza = new Pizza();
		model.addAttribute("pizza", pizza);
		model.addAttribute("promotions", promotions);
		model.addAttribute("ingredients", ingredients);
		
		return "pizza-create";
	}
	
	@PostMapping("/admin/create")
	public String storePizza(@Valid @ModelAttribute("pizza") Pizza pizza, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/pizza/create";
		}
		
		pizzaService.save(pizza);
		return "redirect:/pizza/user";
	}
	
	@GetMapping("/admin/edit/{id}")
	public String editPizza(@PathVariable("id") int id, Model model) {
		
		Optional<Pizza> optPizza = pizzaService.getPizzaById(id);
		Pizza pizza = optPizza.get();
		
		List<Ingredient> ingredients = iService.findAll();
		
		model.addAttribute("ingredients", ingredients);
		model.addAttribute("pizza", pizza);
		
		return "pizza-edit";
	}
	
	@PostMapping("/admin/edit")
	public String updatePizza(@Valid @ModelAttribute("pizza") Pizza pizza, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/pizza/edit/" + pizza.getId();
		}
		
		pizzaService.save(pizza);
		return "redirect:/pizza/user";
	}
	
	@GetMapping("/admin/delete/{id}")
	public String deletePizza(@PathVariable("id") int id) {
		
		pizzaService.deletePizzaById(id);
		
		return "redirect:/pizza/user";
	}
	
	@GetMapping("/user/search")
	public String getSearchPizzaByName(Model model, @RequestParam(name = "query", required = false) String query) {
		
		List<Pizza> pizzas = query == null ? pizzaService.findAll() : pizzaService.findByName(query);
		model.addAttribute("pizzas", pizzas);
		model.addAttribute("query", query);
		return "pizza-search";
	}
}
