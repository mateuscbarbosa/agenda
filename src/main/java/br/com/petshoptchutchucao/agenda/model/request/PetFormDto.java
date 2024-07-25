package br.com.petshoptchutchucao.agenda.model.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.petshoptchutchucao.agenda.model.entities.customer.Gender;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Size;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Species;

public class PetFormDto {

	@NotBlank
	private String name;
	
	@NotNull
	private Species species;
	
	@NotNull
	private Gender gender;
	
	@NotBlank
	private String breed;
	
	@NotNull
	@PastOrPresent
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate birth;
	
	@NotNull
	private Size size;
	
	private String observation;
	
	@NotBlank
	@JsonAlias("customer_id")
	private String customerId;
	
	public PetFormDto() {}

	public PetFormDto(String name, Species species, Gender gender, String breed, LocalDate birth, Size size,
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
