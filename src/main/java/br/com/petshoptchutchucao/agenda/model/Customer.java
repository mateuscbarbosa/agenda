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
	private String address;
	private List<Pet> pets = new ArrayList<>();
	private List<String> contactNumbers = new ArrayList<>();
	private Status status;
	
	public Customer() {}

	public Customer(String name, String address, List<String> contactNumbers, Status status) {
		super();
		this.name = name;
		this.address = address;
		this.contactNumbers = contactNumbers;
		this.status = status;
	}

	public Customer(String name, String address, List<Pet> pets, List<String> contactNumbers, Status status) {
		this.name = name;
		this.address = address;
		this.pets = pets;
		this.contactNumbers = contactNumbers;
		this.status = status;
	}

	public Customer(String id, String name, String address, List<Pet> pets, List<String> contactNumbers,
			Status status) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.pets = pets;
		this.contactNumbers = contactNumbers;
		this.status = status;
	}
	
	public void updateInfo(String id, String name, String address, List<Pet> pets, List<String> contactNumbers, Status status) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.pets = pets;
		this.contactNumbers = contactNumbers;
		this.status = status;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
