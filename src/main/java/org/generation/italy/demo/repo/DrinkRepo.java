package org.generation.italy.demo.repo;

import java.util.List;

import org.generation.italy.demo.pojo.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepo extends JpaRepository<Drink, Integer>{
	
	public List<Drink> findByNameContainingIgnoreCase(String name);
}
