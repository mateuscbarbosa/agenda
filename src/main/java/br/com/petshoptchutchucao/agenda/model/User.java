package br.com.petshoptchutchucao.agenda.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

	@Id
	private String id;
	private String email;
	private String password;
	private String name;
	private List<Profile> profiles = new ArrayList<>();
	private Status status;
	
	public User() {}
	
	public User(String id, String email, String name) {
		this.id = id;
		this.email = email;
		this.name = name;
	}

	public User(String id, String email, String password, String name, List<Profile> profiles, Status status) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.profiles = profiles;
		this.status = status;
	}

	public void addProfile(Profile profile) {
		this.profiles.add(profile);
	}
	
	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
