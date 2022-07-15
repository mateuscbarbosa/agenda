package br.com.petshoptchutchucao.agenda.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.petshoptchutchucao.agenda.model.Pet;
import br.com.petshoptchutchucao.agenda.model.Status;

public class CustumerUpdateFormDto extends CustomerFormDto{

	@NotBlank
	private String id;
	
	@NotNull
	private Status status;
	
	private List<Pet> pets = new ArrayList<>();

	public CustumerUpdateFormDto() {}

	public CustumerUpdateFormDto(String id, Status status, List<Pet> pets) {
		this.id = id;
		this.status = status;
		this.pets = pets;
	}

	public CustumerUpdateFormDto(String id, String name, String address, List<String> contactNumbers, Status status, List<Pet> pets) {
		super(name,address,contactNumbers);
		this.id = id;
		this.status = status;
		this.pets = pets;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}
	
}
