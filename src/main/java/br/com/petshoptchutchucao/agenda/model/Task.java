package br.com.petshoptchutchucao.agenda.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public class Task {

	@Id
	private String id;
	private String name;
	private Spicies spicies;
	private Size size;
	private BigDecimal price;
	
	public Task() {}

	public Task(String id, String name, Spicies spicies, Size size, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.spicies = spicies;
		this.size = size;
		this.price = price;
	}

	public Task(String name, Spicies spicies, Size size, BigDecimal price) {
		this.name = name;
		this.spicies = spicies;
		this.size = size;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Spicies getSpicies() {
		return spicies;
	}

	public void setSpicies(Spicies spicies) {
		this.spicies = spicies;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal value) {
		this.price = value;
	}
	
}
