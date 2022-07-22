package br.com.petshoptchutchucao.agenda.dto;

import br.com.petshoptchutchucao.agenda.model.Spicies;

public class PetOutputDto extends SimplifiedOutputDto{

	private Spicies spicies;
	private String breed;
	
	public PetOutputDto() {}

	public PetOutputDto(String id, String name, Spicies spicies, String breed) {
		super(id, name);
		this.spicies = spicies;
		this.breed = breed;
	}

	public Spicies getSpicies() {
		return spicies;
	}

	public void setSpicies(Spicies spicies) {
		this.spicies = spicies;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}
		
}
