package br.com.petshoptchutchucao.agenda.dto;

import java.math.BigDecimal;

import br.com.petshoptchutchucao.agenda.model.Size;
import br.com.petshoptchutchucao.agenda.model.Spicies;

public class TaskDetailedOutputDto extends TaskOutputDto{
	
	private Spicies spicies;
	private Size size;
	
	public TaskDetailedOutputDto() {}
	public TaskDetailedOutputDto(String id, String name, BigDecimal price, Spicies spicies, Size size) {
		super(id, name, price);
		
		this.spicies = spicies;
		this.size = size;
	}
	
	public Spicies getSpicies() {
		return spicies;
	}
	public Size getSize() {
		return size;
	}
	public void setSpicies(Spicies spicies) {
		this.spicies = spicies;
	}
	public void setSize(Size size) {
		this.size = size;
	}
	
}
