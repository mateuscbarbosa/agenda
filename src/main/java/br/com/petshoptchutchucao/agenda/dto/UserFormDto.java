package br.com.petshoptchutchucao.agenda.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserFormDto {

	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String name;
		
	public UserFormDto() {
	}

	public UserFormDto(String email, String name) {
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
