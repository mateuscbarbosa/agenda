package br.com.petshoptchutchucao.agenda.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.petshoptchutchucao.agenda.model.Status;

public class CustomerDetaliedOutputDto extends CustomerOutputDto{

	private List<PetDetaliedOutputDto> pets;
	private Status status;
	
	public CustomerDetaliedOutputDto() {}
	
	public CustomerDetaliedOutputDto(String id, String name, String address, List<PetDetaliedOutputDto> pets, List<String> contactNumbers, Status status) {
		super(id, name, address, contactNumbers);
		this.pets = pets;
		this.status = status;
	}

	public List<PetDetaliedOutputDto> getPets() {
		return pets;
	}

	public void setPets(List<PetDetaliedOutputDto> pets) {
		this.pets = pets;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
