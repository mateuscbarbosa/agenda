package br.com.petshoptchutchucao.agenda.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.petshoptchutchucao.agenda.model.Gender;
import br.com.petshoptchutchucao.agenda.model.Size;
import br.com.petshoptchutchucao.agenda.model.Spicies;

public class PetDetaliedOutputDto extends PetOutputDto{

	private Gender gender;
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate birth;
	private Size size;
	private String observation;
	
	public PetDetaliedOutputDto() {}

	public PetDetaliedOutputDto(String id, String name, Spicies spicies, Gender gender, String breed, LocalDate birth, Size size, String observation) {
		super(id, name, spicies, breed);
		
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
