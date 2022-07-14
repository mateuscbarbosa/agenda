package br.com.petshoptchutchucao.agenda.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class Customer {

	@Id
	private String id;
	private String name;
	private List<Pet> pets = new ArrayList<>();
	private List<String> contactNumbers = new ArrayList<>();
	
	public Customer() {}

	public Customer(String id, String name, List<Pet> pets, List<String> contactNumbers) {
		this.id = id;
		this.name = name;
		this.pets = pets;
		this.contactNumbers = contactNumbers;
	}
	
	public Customer(String name, List<Pet> pets, List<String> contactNumbers) {
		this.name = name;
		this.pets = pets;
		this.contactNumbers = contactNumbers;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	public List<String> getContactNumbers() {
		return contactNumbers;
	}

	public void setContactNumbers(List<String> contactNumbers) {
		this.contactNumbers = contactNumbers;
	}
	
}
