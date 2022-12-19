package org.generation.italy.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.generation.italy.demo.pojo.Drink;
import org.generation.italy.demo.pojo.Ingredient;
import org.generation.italy.demo.pojo.Pizza;
import org.generation.italy.demo.pojo.Promotion;
import org.generation.italy.demo.pojo.Role;
import org.generation.italy.demo.pojo.User;
import org.generation.italy.demo.service.DrinkService;
import org.generation.italy.demo.service.IngredientService;
import org.generation.italy.demo.service.PizzaService;
import org.generation.italy.demo.service.PromotionService;
import org.generation.italy.demo.service.RoleService;
import org.generation.italy.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLaMiaPizzeriaCrudApplication implements CommandLineRunner{
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private DrinkService drinkService;

	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired 
	private UserService uS;
	
	@Autowired
	private RoleService rS;

	
	public static void main(String[] args) {
		SpringApplication.run(SpringLaMiaPizzeriaCrudApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		Promotion pr1 = new Promotion(LocalDate.parse("2022-10-12"), LocalDate.parse("2022-11-12"), "Super sconto");
		Promotion pr2 = new Promotion(LocalDate.parse("2023-01-01"), LocalDate.parse("2023-01-09"), "Inizio 2023");
		
		promotionService.save(pr1);
		promotionService.save(pr2);
		
		Ingredient i1 = new Ingredient("mozzarella");
		Ingredient i2 = new Ingredient("pomodoro");
		Ingredient i3 = new Ingredient("salame");
		Ingredient i4 = new Ingredient("gorgonzola");
		Ingredient i5 = new Ingredient("carrarmato");
		
		List<Ingredient> ingredients1 = new ArrayList<>();
		ingredients1.add(i1);
		ingredients1.add(i2);
		ingredients1.add(i5);

		List<Ingredient> ingredients2 = new ArrayList<>();
		ingredients2.add(i3);
		ingredients2.add(i4);
		ingredients2.add(i1);

		List<Ingredient> ingredients3 = new ArrayList<>();
		ingredients3.add(i5);

		ingredientService.save(i1);
		ingredientService.save(i2);
		ingredientService.save(i3);
		ingredientService.save(i4);
		ingredientService.save(i5);
		
		Pizza p1 = new Pizza("Margherita", "la classica, la migliore" , 10, pr1, ingredients1);
		Pizza p4 = new Pizza("Margheritona", "la classicone, la migliorona" , 20, pr1, ingredients2);
		Pizza p2 = new Pizza("Capricciosa", "Smettila di piangere" , 14, pr2, ingredients3);
		Pizza p3 = new Pizza("Special1", "la special degli specials" , 20, null);
		
		pizzaService.save(p1);
		pizzaService.save(p2);
		pizzaService.save(p3);
		pizzaService.save(p4);
		
		Drink d1 = new Drink("Coca Cola", "la classica, la migliore" , 2);
		Drink d2 = new Drink("Fanta", "Aranciata, arancione, gialla? Che buona!" , 1);
		Drink d3 = new Drink("Sprite", "Frizzantina, una punta di limone, la gassosa!" , 5);
		
		drinkService.save(d1);
		drinkService.save(d2);
		drinkService.save(d3);
		
		//----------DELETE-------------
		//promotionService.deletePromotionById(1);
		//pizzaService.deletePizzaById(1);
		//ingredientService.deleteIngredientById(1);
		
//		System.out.println("---------------------------");
//		List<Pizza> pizzas = pizzaService.findAll();
//		for (Pizza pizza : pizzas) {
//			System.err.println(pizza + "\n\t" + pizza.getPromotion());
//		}
//		
//		System.out.println("---------------------------");
//		List<Promotion> promotions = promotionService.findAllWPizza();
//		
//		for (Promotion promotion : promotions) {
//			System.err.println(promotion);
//			for (Pizza pizza : promotion.getPizzas()) {
//				System.err.println("\t" + pizza);
//			}
//		}
		
//		System.err.println("---------------------------");
//		List<Pizza> pizzas = pizzaService.findAllWIngredient();
//		for (Pizza pizza : pizzas) {
//			System.err.println(pizza + "\n\t" + pizza.getIngredients());
//		}
//		
//		System.err.println("---------------------------");
//		List<Ingredient> ingredients = ingredientService.findAllWPizza();
//		for (Ingredient i : ingredients) {
//			System.err.println(i +  "\n\t" + i.getPizzas());
//		}
		
		//---------Auth-------------
		Role admin = new Role("ADMIN");
		Role user = new Role("USER");
		
		rS.save(admin);
		rS.save(user);
		
		Set<Role> roles = new HashSet<>();
		roles.add(user);
		roles.add(admin);
		
		User u1 = new User("user","{noop}user",user);
		User a1 = new User("admin","{noop}admin",admin);
		User god = new User("god", "{noop}god", roles);
		
		uS.save(u1);
		uS.save(a1);
		uS.save(god);
		
		

	}

}
