package br.com.petshoptchutchucao.agenda.dto;

import br.com.petshoptchutchucao.agenda.model.Gender;
import br.com.petshoptchutchucao.agenda.model.Spicies;

public class PetOutputDto {

	private String id;
	private String name;
	private Spicies spicies;
	private Gender gender;
	
	public PetOutputDto() {}

	public PetOutputDto(String id, String name, Spicies spicies, Gender gender) {
		this.id = id;
		this.name = name;
		this.spicies = spicies;
		this.gender = gender;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
