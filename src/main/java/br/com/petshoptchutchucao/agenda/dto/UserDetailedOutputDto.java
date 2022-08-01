package br.com.petshoptchutchucao.agenda.dto;

import java.util.List;

import br.com.petshoptchutchucao.agenda.model.Profile;
import br.com.petshoptchutchucao.agenda.model.Status;

public class UserDetailedOutputDto extends UserOutputDto{

	private List<Profile> profiles;
	private Status status;
	
	public UserDetailedOutputDto() {}

	public UserDetailedOutputDto(String id, String email,  String name, List<Profile> profiles, Status status){
		super(id, email, name);
		this.profiles = profiles;
		this.status = status;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
