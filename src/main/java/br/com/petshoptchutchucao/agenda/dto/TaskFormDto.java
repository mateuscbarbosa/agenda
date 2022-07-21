package br.com.petshoptchutchucao.agenda.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.petshoptchutchucao.agenda.model.Size;
import br.com.petshoptchutchucao.agenda.model.Spicies;

public class TaskFormDto {

	@NotBlank
	private String name;
	
	@NotNull
	private Spicies spicies;
	
	@NotNull
	private Size size;
	
	@NotNull
	private BigDecimal price;

	public TaskFormDto() {}
	
	public TaskFormDto(String name, Spicies spicies, Size size, BigDecimal price) {
		this.name = name;
		this.spicies = spicies;
		this.size = size;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toUpperCase();
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

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
