package br.com.petshoptchutchucao.agenda.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pets")
public class Pet {

	private String id;
	private String name;
	private Spicies spicies;
	private Gender gender;
	private String breed;
	private LocalDate birth;
	private Size size;
	private String observation;
	private String customerId;
	
	public Pet() {}

	public Pet(String name, Spicies spicies, Gender gender, String breed, LocalDate birth, Size size,
			String observation, String customerId) {
		this.name = name;
		this.spicies = spicies;
		this.gender = gender;
		this.breed = breed;
		this.birth = birth;
		this.size = size;
		this.observation = observation;
		this.customerId = customerId;
	}

	public Pet(String id, String name, Spicies spicies, Gender gender, String breed, LocalDate birth, Size size,
			String observation, String customerId) {
		this.id = id;
		this.name = name;
		this.spicies = spicies;
		this.gender = gender;
		this.breed = breed;
		this.birth = birth;
		this.size = size;
		this.observation = observation;
		this.customerId = customerId;
	}

	public void updateInfo(String name, Spicies spicies, Gender gender, String breed, LocalDate birth, Size size, String observation, String customerId) {
		this.name = name;
		this.spicies = spicies;
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
