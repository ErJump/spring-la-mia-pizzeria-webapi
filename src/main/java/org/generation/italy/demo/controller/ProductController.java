package org.generation.italy.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.generation.italy.demo.interf.PriceableInt;
import org.generation.italy.demo.pojo.Drink;
import org.generation.italy.demo.pojo.Pizza;
import org.generation.italy.demo.service.DrinkService;
import org.generation.italy.demo.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private DrinkService dS;
	
	@Autowired
	private PizzaService pS;
	
	@GetMapping("/search")
	public String getSearchDrinkByName(Model model, @RequestParam(name = "query", required = false) String query) {
		
		List<Drink> drinks = query == null ? dS.findAll() : dS.findByName(query);
		List<Pizza> pizzas = query == null ? pS.findAll() : pS.findByName(query);
		model.addAttribute("pizzas", pizzas);
 		model.addAttribute("drinks", drinks);
		model.addAttribute("query", query);
		return "product-search";
 	}
	
	@GetMapping("/priceable")
	public String getPriceable(Model model) {
		
		List<PriceableInt> priceables = new ArrayList<>();
		priceables.addAll(dS.findAll());
		priceables.addAll(pS.findAll());

		priceables.sort((p1, p2) -> p1.getPrice() - p2.getPrice());
		model.addAttribute("priceables", priceables);

		return "product-priceable";
	}
}
