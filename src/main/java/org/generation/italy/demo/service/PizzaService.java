package org.generation.italy.demo.service;

import java.util.List;
import java.util.Optional;

import org.generation.italy.demo.pojo.Pizza;
import org.generation.italy.demo.repo.PizzaRepo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PizzaService {
	
	@Autowired
	private PizzaRepo pizzaRepo;
	
	public Pizza save(Pizza pizza) {
		return pizzaRepo.save(pizza);
	}
	
	public List<Pizza> findAll() {
		return pizzaRepo.findAll();
	}
	
	public Optional<Pizza> getPizzaById(int id) {
		return pizzaRepo.findById(id);
	}
	
	public void deletePizzaById(int id) {
		pizzaRepo.deleteById(id);
	}
	
	public List<Pizza> findByName(String name) {
		return pizzaRepo.findByNameContainingIgnoreCase(name);
	}
	
	@Transactional
	public List<Pizza> findAllWIngredient() {
		List<Pizza> pizzas = pizzaRepo.findAll();
		
		for (Pizza p : pizzas) {
			Hibernate.initialize(p.getIngredients());
		}
		
		return pizzas;
		
	}
}
