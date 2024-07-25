package br.com.petshoptchutchucao.agenda.model.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.petshoptchutchucao.agenda.model.entities.customer.Size;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Species;

public class TaskFormDto {

	@NotBlank
	private String name;
	
	@NotNull
	private Species species;
	
	@NotNull
	private Size size;
	
	@NotNull
	private BigDecimal price;

	public TaskFormDto() {}
	
	public TaskFormDto(String name, Species species, Size size, BigDecimal price) {
		this.name = name;
		this.species = species;
		this.size = size;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toUpperCase();
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

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
