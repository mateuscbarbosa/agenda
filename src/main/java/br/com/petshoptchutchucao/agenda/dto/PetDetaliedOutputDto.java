package br.com.petshoptchutchucao.agenda.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.petshoptchutchucao.agenda.model.Gender;
import br.com.petshoptchutchucao.agenda.model.Size;
import br.com.petshoptchutchucao.agenda.model.Spicies;

public class PetDetaliedOutputDto extends PetOutputDto{

	private String breed;
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate birth;
	private Size size;
	private String observation;
	
	public PetDetaliedOutputDto() {}

	public PetDetaliedOutputDto(String id, String name, Spicies spicies, Gender gender, String breed, LocalDate birth, Size size, String observation) {
		super(id, name, spicies, gender);
		
		this.breed = breed;
		this.birth = birth;
		this.size = size;
		this.observation = observation;
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
	
}
