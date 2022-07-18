package br.com.petshoptchutchucao.agenda.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.petshoptchutchucao.agenda.model.Gender;
import br.com.petshoptchutchucao.agenda.model.Size;
import br.com.petshoptchutchucao.agenda.model.Spicies;

public class PetFormDto {

	@NotBlank
	private String name;
	
	@NotNull
	private Spicies spicies;
	
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

	public PetFormDto(String name, Spicies spicies, Gender gender, String breed, LocalDate birth, Size size,
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
