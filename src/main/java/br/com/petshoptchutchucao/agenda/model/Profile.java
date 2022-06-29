package br.com.petshoptchutchucao.agenda.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profiles")
public class Profile {

	@Id
	private String id;
	private String description;
	
	public Profile() {};
	
	public Profile(String id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
}
