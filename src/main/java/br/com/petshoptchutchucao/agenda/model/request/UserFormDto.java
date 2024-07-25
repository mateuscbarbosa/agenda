package br.com.petshoptchutchucao.agenda.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class UserFormDto {

	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String name;
	
	@NotEmpty
	private Integer profiles[];
		
	public UserFormDto() {
	}

	public UserFormDto(String email, String name,Integer[] profiles) {
		this.email = email;
		this.name = name;
		this.profiles = profiles;
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

	public Integer[] getProfiles() {
		return profiles;
	}

	public void setProfiles(Integer[] profiles) {
		this.profiles = profiles;
	}
	
}
