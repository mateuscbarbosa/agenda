package br.com.petshoptchutchucao.agenda.model.response;

import java.util.List;

import br.com.petshoptchutchucao.agenda.model.entities.user.Status;

public class CustomerDetailedOutputDto extends CustomerOutputDto {

	private List<PetDetailedOutputDto> pets;
	private Status status;
	
	public CustomerDetailedOutputDto() {}
	
	public CustomerDetailedOutputDto(String id, String name, String address, List<PetDetailedOutputDto> pets, List<String> contactNumbers, Status status) {
		super(id, name, address, contactNumbers);
		this.pets = pets;
		this.status = status;
	}

	public List<PetDetailedOutputDto> getPets() {
		return pets;
	}

	public void setPets(List<PetDetailedOutputDto> pets) {
		this.pets = pets;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
