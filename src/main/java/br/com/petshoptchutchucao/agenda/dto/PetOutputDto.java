package br.com.petshoptchutchucao.agenda.dto;

import br.com.petshoptchutchucao.agenda.model.Gender;
import br.com.petshoptchutchucao.agenda.model.Spicies;

public class PetOutputDto extends SimplifiedOutputDto{

	private Spicies spicies;
	private Gender gender;
	
	public PetOutputDto() {}

	public PetOutputDto(String id, String name, Spicies spicies, Gender gender) {
		super(id, name);
		this.spicies = spicies;
		this.gender = gender;
	}

	public Spicies getSpicies() {
		return spicies;
	}

	public void setSpicies(Spicies spicies) {
		this.spicies = spicies;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
		
}
