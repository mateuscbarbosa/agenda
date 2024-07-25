package br.com.petshoptchutchucao.agenda.model.entities.schedule;

import java.math.BigDecimal;

import br.com.petshoptchutchucao.agenda.model.entities.customer.Size;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Species;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public class Task {

	@Id
	private String id;
	private String name;
	private Species species;
	private Size size;
	private BigDecimal price;
	
	public Task() {}

	public Task(String id, String name, Species species, Size size, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.species = species;
		this.size = size;
		this.price = price;
	}

	public Task(String name, Species species, Size size, BigDecimal price) {
		this.name = name;
		this.species = species;
		this.size = size;
		this.price = price;
	}
	
	public void updateInfo(String name, Species species, Size size, BigDecimal price) {
		this.name = name;
		this.species = species;
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

	public Species getSpicies() {
		return species;
	}

	public void setSpicies(Species species) {
		this.species = species;
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
