package org.generation.italy.demo.pojo;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Promotion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	@NotNull(message = "la data di inizio non può essere vuota")
	private LocalDate startDate; 
	
	@Column
	@NotNull(message = "la data di fine non può essere vuota")
	private LocalDate endDate; 
	
	@Column(unique = true)
	@NotNull(message = "il titolo non può essere vuoto")
	private String title;
	
	@OneToMany(mappedBy = "promotion", cascade = CascadeType.REMOVE)
	private List<Pizza> pizzas;
	
	public Promotion() { }
	public Promotion(LocalDate startDate, LocalDate endDate, String title) {
		setStartDate(startDate);
		setEndDate(endDate);
		setTitle(title);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public List<Pizza> getPizzas() {
		return pizzas;
	}
	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	
	@Override
	public String toString() {
		return "(" + getId() + ")" + " " + getTitle() + " - " + getStartDate() + " / " + getEndDate();
	}
	
	
	
	
}
