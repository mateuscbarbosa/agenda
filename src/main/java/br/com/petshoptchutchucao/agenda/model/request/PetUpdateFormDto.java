package br.com.petshoptchutchucao.agenda.model.request;

import java.time.LocalDate;

import br.com.petshoptchutchucao.agenda.model.entities.customer.Gender;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Size;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Species;

public class PetUpdateFormDto extends PetFormDto {

	private String id;

	public PetUpdateFormDto() {	}

	public PetUpdateFormDto(String id, String name, Species species, Gender gender, String breed, LocalDate birth, Size size, String observation, String customerId) {
		super(name, species, gender, breed, birth, size, observation, customerId);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
