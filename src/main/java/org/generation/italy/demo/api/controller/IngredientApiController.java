package org.generation.italy.demo.api.controller;

import java.util.List;

import org.generation.italy.demo.pojo.Ingredient;
import org.generation.italy.demo.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1/ingredienti")
@CrossOrigin("*")
public class IngredientApiController {
	
	@Autowired
	private PizzaService pS;
	
	@GetMapping("/by/pizza/{id}")
	public List<Ingredient> getIngredientsByPizzaId(@PathVariable("id") int id) {
		System.err.println("pizza id: " + id);
		return pS.getPizzaById(id).get().getIngredients();
	}
}
