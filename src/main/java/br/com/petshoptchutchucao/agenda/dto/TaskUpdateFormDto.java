package br.com.petshoptchutchucao.agenda.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import br.com.petshoptchutchucao.agenda.model.Size;
import br.com.petshoptchutchucao.agenda.model.Spicies;

public class TaskUpdateFormDto extends TaskFormDto{

	@NotBlank
	private String id;

	public TaskUpdateFormDto() {}

	public TaskUpdateFormDto(String id, String name, Spicies spicies, Size size, BigDecimal price) {
		super(name, spicies, size, price);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
