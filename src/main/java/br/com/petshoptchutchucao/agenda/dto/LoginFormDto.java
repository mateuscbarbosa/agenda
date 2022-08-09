package br.com.petshoptchutchucao.agenda.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAlias;

public class LoginFormDto {

	@NotBlank
	@JsonAlias("e-mail")
	private String email;
	
	@NotBlank
	@JsonAlias("senha")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
