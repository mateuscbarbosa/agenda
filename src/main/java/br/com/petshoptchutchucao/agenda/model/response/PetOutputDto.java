package br.com.petshoptchutchucao.agenda.model.response;

import br.com.petshoptchutchucao.agenda.model.entities.customer.Species;

public class PetOutputDto extends SimplifiedOutputDto {

	private Species species;
	private String breed;
	
	public PetOutputDto() {}

	public PetOutputDto(String id, String name, Species species, String breed) {
		super(id, name);
		this.species = species;
		this.breed = breed;
	}

	public Species getSpicies() {
		return species;
	}

	public void setSpicies(Species species) {
		this.species = species;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}
		
}
