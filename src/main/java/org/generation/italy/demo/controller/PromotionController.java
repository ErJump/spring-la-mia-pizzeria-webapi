package org.generation.italy.demo.controller;

import java.util.List;

import org.generation.italy.demo.pojo.Pizza;
import org.generation.italy.demo.pojo.Promotion;
import org.generation.italy.demo.service.PizzaService;
import org.generation.italy.demo.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/promotion")
public class PromotionController {
	
	@Autowired
	private PromotionService prService;
	
	@Autowired 
	private PizzaService pzService;
	
	@GetMapping
	public String index(Model model) {
		
		List<Promotion> promotions = prService.findAllWPizza();
		model.addAttribute("promotions", promotions);
		return "promotion-index";
	}
	
	@GetMapping("/create")
	public String getPromotionCreate(Model model) {
		
		Promotion promotion = new Promotion();
		List<Pizza> pizzas = pzService.findAll();
		model.addAttribute("promotion", promotion);
		model.addAttribute("pizzas", pizzas);
		
		return "promotion-create";
	}
	
	@PostMapping("/create")
	public String storePromotion(@Valid Promotion promotion) {
		List<Pizza> promotionPizzas = promotion.getPizzas();
		for (Pizza pizza : promotionPizzas) {
			pizza.setPromotion(promotion);
		}
		prService.save(promotion);
		
		return "redirect:/promotion";
	}
}
