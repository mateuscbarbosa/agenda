package br.com.petshoptchutchucao.agenda.model.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import br.com.petshoptchutchucao.agenda.model.entities.customer.Size;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Species;

public class TaskUpdateFormDto extends TaskFormDto {

	@NotBlank
	private String id;

	public TaskUpdateFormDto() {}

	public TaskUpdateFormDto(String id, String name, Species species, Size size, BigDecimal price) {
		super(name, species, size, price);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
