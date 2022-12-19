package org.generation.italy.demo.api.controller;

import java.util.List;

import org.generation.italy.demo.pojo.Pizza;
import org.generation.italy.demo.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/1/pizza")
@CrossOrigin("*")
public class PizzaApiController {
	
	@Autowired
	private PizzaService pS;
	
	@GetMapping("/allPizzas")
	public List<Pizza> getAll() {
		return pS.findAll();
	}
	
	@PostMapping("/update/{id}")
	public Pizza updatePizza(@PathVariable("id") int id, @Valid @RequestBody Pizza pizza) {
		
		System.err.println(pizza);
		
		Pizza oldP = pS.getPizzaById(id).get();
		pizza.setIngredients(oldP.getIngredients());
		
		pS.save(pizza);
		
		return pS.getPizzaById(id).get(); 
	}
	
	@GetMapping("/delete/{id}")
	public boolean deletePizza(@PathVariable("id") int id) {
		
		try {
			pS.deletePizzaById(id);
		} catch(Exception e) {return false; }
		return true;
	}
	
	@PostMapping("/create")
	public Pizza createPizza(@Valid @RequestBody Pizza pizza) {
		System.err.println(pizza);
		Pizza newPizza = pS.save(pizza);
		return newPizza;
	}
	
	@GetMapping("/search/{query}")
	public List<Pizza> getSearchPizzaByName(@PathVariable("query") String query) {
		System.err.println(query);
		List<Pizza> pizzas = query == null ? pS.findAll() : pS.findByName(query);
		return pizzas;
	}
}
