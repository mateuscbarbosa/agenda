package br.com.petshoptchutchucao.agenda.model.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.petshoptchutchucao.agenda.model.entities.customer.Gender;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Size;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Species;

public class PetDetailedOutputDto extends PetOutputDto {

	private Gender gender;
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate birth;
	private Size size;
	private String observation;
	
	public PetDetailedOutputDto() {}

	public PetDetailedOutputDto(String id, String name, Species species, Gender gender, String breed, LocalDate birth, Size size, String observation) {
		super(id, name, species, breed);
		
		this.gender = gender;
		this.birth = birth;
		this.size = size;
		this.observation = observation;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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
	
}
