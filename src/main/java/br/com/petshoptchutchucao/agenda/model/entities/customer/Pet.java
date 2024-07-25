package br.com.petshoptchutchucao.agenda.model.entities.customer;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pets")
public class Pet {

	private String id;
	private String name;
	private Species species;
	private Gender gender;
	private String breed;
	private LocalDate birth;
	private Size size;
	private String observation;
	private String customerId;
	
	public Pet() {}

	public Pet(String name, Species species, Gender gender, String breed, LocalDate birth, Size size,
			   String observation, String customerId) {
		this.name = name;
		this.species = species;
		this.gender = gender;
		this.breed = breed;
		this.birth = birth;
		this.size = size;
		this.observation = observation;
		this.customerId = customerId;
	}

	public Pet(String id, String name, Species species, Gender gender, String breed, LocalDate birth, Size size,
			   String observation, String customerId) {
		this.id = id;
		this.name = name;
		this.species = species;
		this.gender = gender;
		this.breed = breed;
		this.birth = birth;
		this.size = size;
		this.observation = observation;
		this.customerId = customerId;
	}

	public void updateInfo(String name, Species species, Gender gender, String breed, LocalDate birth, Size size, String observation, String customerId) {
		this.name = name;
		this.species = species;
		this.gender = gender;
		this.breed = breed;
		this.birth = birth;
		this.size = size;
		this.observation = observation;
		this.customerId = customerId;
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

	public Species getSpicies() {
		return species;
	}

	public void setSpicies(Species species) {
		this.species = species;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
	
	public String getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
