package org.generation.italy.demo.controller;

import java.util.List;
import java.util.Optional;

import org.generation.italy.demo.pojo.Ingredient;
import org.generation.italy.demo.pojo.Pizza;
import org.generation.italy.demo.service.IngredientService;
import org.generation.italy.demo.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {
	
	@Autowired
	private IngredientService iS;
	
	@Autowired
	private PizzaService pS;
	
	@GetMapping
	public String index(Model model) {
		
		List<Ingredient> ingredients = iS.findAllWPizza();
		model.addAttribute("ingredients", ingredients);
		return "ingredient-index";
	}
	
	@GetMapping("/create")
	public String getIngredientCreate(Model model) {
		
		Ingredient i = new Ingredient();
		List<Pizza> pizzas = pS.findAll();
		model.addAttribute("ingredient", i);
		model.addAttribute("pizzas", pizzas);
		return "ingredient-create";
	}
	
	@PostMapping("/create")
	public String storeIngredient(@Valid Ingredient ingredient) {
		
		List<Pizza> ingredientPizzas = ingredient.getPizzas();
		for (Pizza p : ingredientPizzas)
			p.getIngredients().add(ingredient);
		iS.save(ingredient);
		return "redirect:/ingredient";
	}
	
	@GetMapping("/edit/{id}")
	public String editIngredient(@PathVariable("id") int id, Model model) {
		
		Ingredient i = iS.getIngredientById(id).get();
		List<Pizza> pizzas = pS.findAll();
		model.addAttribute("ingredient", i);
		model.addAttribute("pizzas", pizzas);
		return "ingredient-edit";
	}
	
	@PostMapping("/edit")
	public String updateIngredient(@Valid Ingredient ingredient) {
		
		Optional<Ingredient> optI = iS.getIngredientById(ingredient.getId());
		Ingredient ing = optI.get();
		
		for (Pizza pizza : ing.getPizzas()) {
			pizza.removeIngredients(ing);
		}
		
		List<Pizza> ingredientPizzas = ingredient.getPizzas();
		for (Pizza p : ingredientPizzas) {
			p.addIngredients(ingredient);
		}
		
		iS.save(ingredient);
		return "redirect:/ingredient";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteIngredient(@PathVariable("id") int id) {
		
		Ingredient i = iS.getIngredientById(id).get();
		for (Pizza pizza : i.getPizzas())
			pizza.removeIngredients(i);
			
		iS.deleteIngredientById(id);
		return "redirect:/ingredient";
	}
	
}
