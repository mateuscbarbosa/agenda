package br.com.petshoptchutchucao.agenda.model.response;

import java.math.BigDecimal;

import br.com.petshoptchutchucao.agenda.model.entities.customer.Size;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Species;

public class TaskDetailedOutputDto extends TaskOutputDto {
	
	private Species species;
	private Size size;
	
	public TaskDetailedOutputDto() {}
	public TaskDetailedOutputDto(String id, String name, BigDecimal price, Species species, Size size) {
		super(id, name, price);
		
		this.species = species;
		this.size = size;
	}
	
	public Species getSpicies() {
		return species;
	}
	public Size getSize() {
		return size;
	}
	public void setSpicies(Species species) {
		this.species = species;
	}
	public void setSize(Size size) {
		this.size = size;
	}
	
}
