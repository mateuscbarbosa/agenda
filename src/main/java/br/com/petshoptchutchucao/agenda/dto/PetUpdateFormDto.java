package br.com.petshoptchutchucao.agenda.dto;

import java.time.LocalDate;

import br.com.petshoptchutchucao.agenda.model.Gender;
import br.com.petshoptchutchucao.agenda.model.Size;
import br.com.petshoptchutchucao.agenda.model.Spicies;

public class PetUpdateFormDto extends PetFormDto{

	private String id;

	public PetUpdateFormDto() {	}

	public PetUpdateFormDto(String id, String name, Spicies spicies, Gender gender, String breed, LocalDate birth, Size size, String observation, String customerId) {
		super(name, spicies, gender, breed, birth, size, observation, customerId);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
