package br.com.petshoptchutchucao.agenda.model.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

public class CustomerFormDto {

	@NotBlank
	private String name;
	
	@NotBlank
	private String address;
	
	private List<String> contactNumbers = new ArrayList<>();
	
	public CustomerFormDto() {}

	public CustomerFormDto(String name, String address, List<String> contactNumbers) {
		this.name = name;
		this.address = address;
		this.contactNumbers = contactNumbers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getContactNumbers() {
		return contactNumbers;
	}

	public void setContactNumbers(List<String> contactNumbers) {
		this.contactNumbers = contactNumbers;
	}
}
