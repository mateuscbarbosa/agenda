package br.com.petshoptchutchucao.agenda.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.petshoptchutchucao.agenda.model.Status;

public class UserUpdateFormDto extends UserFormDto{

	@NotBlank
	private String id;
	
	@NotNull
	private Status status;
	
	@NotBlank
	private String password;

	public UserUpdateFormDto() {}

	public UserUpdateFormDto(String id, Status status, String password) {
		this.id = id;
		this.status = status;
		this.password = password;
	}
	
	public UserUpdateFormDto(String id, String email, String name, Integer[] profiles, Status status, String password) {
		super(email, name, profiles);
		this.id = id;
		this.status = status;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
