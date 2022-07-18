package br.com.petshoptchutchucao.agenda.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.petshoptchutchucao.agenda.model.Status;

public class CustomerUpdateFormDto extends CustomerFormDto{

	@NotBlank
	private String id;
	
	@NotNull
	private Status status;
	
	public CustomerUpdateFormDto() {}

	public CustomerUpdateFormDto(String id, Status status) {
		this.id = id;
		this.status = status;
	}

	public CustomerUpdateFormDto(String id, String name, String address, List<String> contactNumbers, Status status) {
		super(name,address,contactNumbers);
		this.id = id;
		this.status = status;
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
	
}
