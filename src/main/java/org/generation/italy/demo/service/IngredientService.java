package org.generation.italy.demo.service;

import java.util.List;
import java.util.Optional;

import org.generation.italy.demo.pojo.Ingredient;
import org.generation.italy.demo.repo.IngredientRepo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class IngredientService {

	@Autowired
	private IngredientRepo iR;
	
	public void save(Ingredient i) {
		iR.save(i);
	}
	
	public List<Ingredient> findAll() {
		return iR.findAll();
	}
	
	public Optional<Ingredient> getIngredientById(int id) {
		return iR.findById(id);
	}
	
	public void deleteIngredientById(int id) {
		iR.deleteById(id);
	}
	
	@Transactional
	public List<Ingredient> findAllWPizza() {
		List<Ingredient> ingredients = iR.findAll();
		
		for (Ingredient i : ingredients) {
			Hibernate.initialize(i.getPizzas());
		}
		
		return ingredients;
		
	}
}
